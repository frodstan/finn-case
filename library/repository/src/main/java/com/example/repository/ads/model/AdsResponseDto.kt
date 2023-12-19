package com.example.repository.ads.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AdsResponseDto(
    val items: List<AdDto>
)

@JsonClass(generateAdapter = true)
data class AdDto(
    val id: String,
    val description: String?,
    val url: String?,
    @Json(name = "ad-type")
    val adType: String,
    val location: String?,
    val type: String?,
    val price: PriceDto?,
    val image: ImageDto?,
    val score: Double?,
    val version: String?,
    val favourite: AdFavouriteDto?
)

@JsonClass(generateAdapter = true)
data class PriceDto(
    val value: Int?,
    val total: Int?,
)

@JsonClass(generateAdapter = true)
data class ImageDto(
    val url: String?,
    val height: Int?,
    val width: Int?,
    val type: String?,
    val scalable: Boolean?,
)

@JsonClass(generateAdapter = true)
data class AdFavouriteDto(
    val itemId: String?,
    val itemType: String?,
)

