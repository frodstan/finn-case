package com.example.ads.model


data class AdsUi(
    val filterFavourites: Boolean,
    val content: AdsContentUi
)

sealed interface AdsContentUi {
    data object Loading : AdsContentUi
    data object Error : AdsContentUi //TODO: Add descriptive fields about what the error is and display
    data class AdsContent(
        val items: List<AdItemUi>
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