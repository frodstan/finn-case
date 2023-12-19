package com.example.finncase

import android.app.Application
import com.example.finncase.di.KoinModules
import org.koin.core.context.startKoin

class FinnApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        createKoin()
    }

    private fun createKoin() {
        startKoin {
            modules(KoinModules.getModules())
        }
    }
}