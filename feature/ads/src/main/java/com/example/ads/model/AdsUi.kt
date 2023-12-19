package com.example.ads.model

sealed interface AdsUi {
    data object Loading : AdsUi
    data object Error : AdsUi //TODO: Add descriptive fields about what the error is and display
    data class Ads(
        val items: List<AdItemUi>
    )
}

data class AdItemUi(
    val id: String
)