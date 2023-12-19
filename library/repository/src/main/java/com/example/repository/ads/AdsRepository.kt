package com.example.repository.ads

import com.example.repository.ads.cache.AdsCache
import com.example.repository.ads.model.AdsResponseDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AdsRepository(
    private val api: AdsApi,
    private val adsCache: AdsCache,
) {
    suspend fun fetchAds(): AdsResponseDto? = withContext(Dispatchers.IO) {
        try {
            val adsResponse = api.getAds()
            if (adsResponse != null) {
                adsCache.store(adsResponse)
            }

            adsResponse ?: adsCache.restore()
        } catch (err: Exception) {
            //TODO: Log error in a meaningful way & return something describing the error
            adsCache.restore()
        }
    }
}