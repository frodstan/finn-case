package com.example.repository.ads.cache

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.repository.ads.model.AdsResponseDto
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.Moshi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.mapLatest

private val Context.adsDatastore by preferencesDataStore(name = "ads_cache")

@OptIn(ExperimentalCoroutinesApi::class)
class AdsCache(
    private val context: Context,
    private val moshi: Moshi,
) {
    private val adsCacheKey get() = stringPreferencesKey("ads")
    private val adsAdapter by lazy { moshi.adapter(AdsResponseDto::class.java) }

    suspend fun putCache(responseDto: AdsResponseDto) {
        try {
            val stringRepresentation = adsAdapter.toJson(responseDto)
            context.adsDatastore.edit { preferences ->
                preferences[adsCacheKey] = stringRepresentation
            }
        } catch (err: AssertionError) {
            //TODO: Log failed saving to cache
        }
    }

    suspend fun getCache(): AdsResponseDto? = context.adsDatastore
        .data
        .mapLatest {
            val jsonCache = it[adsCacheKey]
            if (jsonCache != null) {
                try {
                   adsAdapter.fromJson(jsonCache)
                } catch (err: JsonDataException) {
                    //TODO: Log something meaningful
                    null
                }
            } else {
                null
            }
        }
        .first()

}