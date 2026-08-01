package com.example.chefia

import android.app.Application
import com.example.chefia.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class ChefIAApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@ChefIAApplication)
            modules(appModule)
        }
    }
}