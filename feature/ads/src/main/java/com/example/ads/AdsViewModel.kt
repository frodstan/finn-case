package com.example.ads

import androidx.lifecycle.ViewModel
import com.example.repository.favouriteads.FavouriteAdsRepository
import com.example.repository.ads.AdsRepository
import kotlinx.coroutines.flow.StateFlow

class AdsViewModel(
    private val adsRepository: AdsRepository,
    private val favouriteAdsRepository: FavouriteAdsRepository
) : ViewModel() {
    val adsState: StateFlow<>
}