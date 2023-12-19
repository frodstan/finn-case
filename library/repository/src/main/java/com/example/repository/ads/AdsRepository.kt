package com.example.repository.ads

import com.example.repository.ads.model.AdsResponseDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AdsRepository(
    private val api: AdsApi
) {
    suspend fun fetchAds(): AdsResponseDto? = withContext(Dispatchers.IO) {
        try {
            api.getAds()
        } catch (err: Exception) {
            //TODO: Log error in a meaningful way & return something describing the error
            null
        }
    }
}