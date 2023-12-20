package com.example.finncase.di

import com.example.ads.AdsViewModel
import com.example.repository.ads.AdsRepository
import com.example.repository.ads.cache.AdsCache
import com.example.repository.favouriteads.FavouriteAdsRepository
import com.example.repository.favouriteads.room.FavouritesDatabase
import com.example.repository.network.ApiService
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object KoinModules {
    fun getModules() = listOf(
        adsModule(),
        repositoryModule()
    )

    private fun adsModule() = module {
        viewModel {
            AdsViewModel(
                adsRepository = get(),
                favouriteAdsRepository = get()
            )
        }
    }

    private fun repositoryModule() = module {
        single { ApiService() }
        factory {
            val apiService: ApiService = get()
            AdsCache(androidContext(), apiService.moshi)
        }

        factory {
            val apiService: ApiService = get()
            AdsRepository(
                api = apiService.createApi(),
                adsCache = get()
            )
        }

        single { FavouriteAdsRepository(FavouritesDatabase.makeDao(androidContext())) }
    }
}