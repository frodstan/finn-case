package com.example.ads.model


data class AdsUi(
    val isFavouritesFiltered: Boolean,
    val content: AdsContentUi
)

sealed interface AdsContentUi {
    data object Loading : AdsContentUi
    data object Error : AdsContentUi //TODO: Add descriptive fields about what the error is and display
    data class AdsContent(
        val items: List<AdItemUi>,
        val isOffline: Boolean,
    ) : AdsContentUi
}

data class AdItemUi(
    val id: String,
    val favouriteItemType: String?,
    val isFavourite: Boolean,
    val title: String?,
    val imageUrl: String?,
    val location: String?,
    val price: String?,
)