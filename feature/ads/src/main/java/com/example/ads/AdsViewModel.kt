package com.example.ads

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ads.model.AdItemUi
import com.example.ads.model.AdsContentUi
import com.example.ads.model.AdsUi
import com.example.repository.favouriteads.FavouriteAdsRepository
import com.example.repository.ads.AdsRepository
import com.example.repository.ads.model.AdsResponseDto
import com.example.repository.favouriteads.room.FavouriteAdDb
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AdsViewModel(
    private val adsRepository: AdsRepository,
    private val favouriteAdsRepository: FavouriteAdsRepository
) : ViewModel() {
    private var refreshJob: Job? = null
        set(value) {
            field?.cancel()
            field = value
        }

    private val mutableRemoteAdsState: MutableStateFlow<AdsResponseDto?> = MutableStateFlow(null)
    private val mutableFilterFavouriteState: MutableStateFlow<Boolean> = MutableStateFlow(false)

    val adsState: StateFlow<AdsUi> = combine(
        mutableFilterFavouriteState,
        mutableRemoteAdsState,
        favouriteAdsRepository.getFavouritesFlow()
    ) { filterFavourites, remoteAds, favouriteAds ->
        val content = mapAdsState(filterFavourites, remoteAds, favouriteAds)
        AdsUi(
            filterFavourites = filterFavourites,
            content = content
        )
    }
    .stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = AdsUi(false, AdsContentUi.Loading)
    )

    init { refreshAds() }

    private fun refreshAds() {
        refreshJob = viewModelScope.launch {
            mutableRemoteAdsState.value = adsRepository.fetchAds()
        }
    }

    private fun mapAdsState(
        filterFavourites: Boolean,
        adsResponse: AdsResponseDto?,
        favouriteAds: List<FavouriteAdDb>
    ): AdsContentUi = if (adsResponse != null) {
        val favouriteIds = favouriteAds.map { it.id }
        val adsToShow = if (filterFavourites) {
            adsResponse.items.filter { adsItem -> favouriteIds.contains(adsItem.id) }
        } else {
            adsResponse.items
        }

        AdsContentUi.AdsContent(
            items = adsToShow
                .map { adsItem ->
                    AdItemUi(
                        id = adsItem.id,
                        isFavourite = favouriteIds.contains(adsItem.id),
                        title = adsItem.description,
                        favouriteItemType = adsItem.favourite?.itemType
                    )
            }
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