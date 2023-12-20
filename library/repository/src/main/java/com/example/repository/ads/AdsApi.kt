package com.example.repository.ads

import com.example.repository.ads.model.AdsResponseDto
import retrofit2.http.GET

interface AdsApi {
    @GET("baldermork/6a1bcc8f429dcdb8f9196e917e5138bd/raw/discover.json")
    suspend fun getAds(): AdsResponseDto
}