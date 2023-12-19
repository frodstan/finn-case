package com.example.repository.ads

import com.example.repository.DataResult
import com.example.repository.ads.cache.AdsCache
import com.example.repository.ads.model.AdsResponseDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AdsRepository(
    private val api: AdsApi,
    private val adsCache: AdsCache,
) {
    suspend fun fetchAds(): DataResult<AdsResponseDto> = withContext(Dispatchers.IO) {
        try {
            val adsResponse = requireNotNull(api.getAds())
            adsCache.store(adsResponse)
            DataResult.Success(adsResponse, isOfflineCache = false)
        } catch (err: Throwable) {
            val cache = adsCache.restore()
            if (cache != null) {
                DataResult.Success(data = cache, isOfflineCache = true)
            } else {
                DataResult.Error(err)
            }
        }
    }
}