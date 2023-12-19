package com.example.finncase

import android.app.Application
import com.example.finncase.di.KoinModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class FinnApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        createKoin()
    }

    private fun createKoin() {
        startKoin {
            androidContext(this@FinnApplication)
            modules(KoinModules.getModules())
        }
    }
}