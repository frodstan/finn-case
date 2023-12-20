package com.example.ads

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ads.model.AdItemUi
import com.example.ads.model.AdsContentUi
import com.example.ads.model.AdsUi
import com.example.repository.RepositoryConstants
import com.example.repository.DataResult
import com.example.repository.ads.AdsRepository
import com.example.repository.ads.model.AdsResponseDto
import com.example.repository.favouriteads.FavouriteAdsRepository
import com.example.repository.favouriteads.room.FavouriteAdDb
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.Locale

class AdsViewModel(
    private val adsRepository: AdsRepository,
    private val favouriteAdsRepository: FavouriteAdsRepository
) : ViewModel() {
    private var refreshJob: Job? = null
        set(value) {
            field?.cancel()
            field = value
        }

    private val mutableRemoteAdsState: MutableStateFlow<DataResult<AdsResponseDto>?> =
        MutableStateFlow(null)
    private val mutableFilterFavouriteState: MutableStateFlow<Boolean> =
        MutableStateFlow(false)

    val adsState: StateFlow<AdsUi> = combine(
        mutableFilterFavouriteState,
        mutableRemoteAdsState.filterNotNull(),
        favouriteAdsRepository.getFavouritesFlow()
    ) { filterFavourites, remoteAds, favouriteAds ->
        val content = mapAdsState(filterFavourites, remoteAds, favouriteAds)
        AdsUi(
            isFavouritesFiltered = filterFavourites,
            content = content
        )
    }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = AdsUi(false, AdsContentUi.Loading)
        )

    init {
        refreshAds()
    }

    fun refreshAds() {
        refreshJob = viewModelScope.launch {
            mutableRemoteAdsState.value = adsRepository.fetchAds()
        }
    }

    private fun mapAdsState(
        filterFavourites: Boolean,
        adsResponse: DataResult<AdsResponseDto>,
        favouriteAds: List<FavouriteAdDb>
    ): AdsContentUi = if (adsResponse is DataResult.Success) {
        val favouriteIds = favouriteAds.map { it.id }
        val adsToShow = if (filterFavourites || adsResponse.isOfflineCache) {
            adsResponse.data.items.filter { adsItem -> favouriteIds.contains(adsItem.id) }
        } else {
            adsResponse.data.items
        }
        val currencyFormatter = NumberFormat
            .getCurrencyInstance(Locale("nb", "NO"))
            .apply { maximumFractionDigits = 0 }

        AdsContentUi.AdsContent(
            items = adsToShow
                .map { adsItem ->
                    AdItemUi(
                        id = adsItem.id,
                        isFavourite = favouriteIds.contains(adsItem.id),
                        title = adsItem.description,
                        favouriteItemType = adsItem.favourite?.itemType,
                        location = adsItem.location,
                        price = adsItem.price?.total?.let { totalPrice ->
                            currencyFormatter.format(totalPrice)
                        },
                        imageUrl = adsItem.image?.url?.let { imagePath ->
                            RepositoryConstants.IMAGE_BASE_URL + imagePath
                        },
                    )
                },
            isOffline = adsResponse.isOfflineCache
        )
    } else {
        AdsContentUi.Error
    }

    fun onFilterFavouritesClick() {
        mutableFilterFavouriteState.update { state -> !state }
    }

    fun onItemFavourite(item: AdItemUi) {
        viewModelScope.launch {
            val favouriteAdDb = FavouriteAdDb(id = item.id, itemType = item.favouriteItemType)
            if (item.isFavourite) {
                favouriteAdsRepository.removeFavourite(favouriteAdDb)
            } else {
                favouriteAdsRepository.insertFavourite(favouriteAdDb)
            }
        }
    }
}