package com.example.chefia

import android.app.Application
import com.example.chefia.core.firebase.AppCheckInitializer
import com.example.chefia.di.appModule
import com.example.chefia.di.databaseModule
import com.example.chefia.di.networkModule
import com.example.chefia.di.recipeModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class ChefIAApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        AppCheckInitializer.initialize(this)

        startKoin {
            androidLogger()
            androidContext(this@ChefIAApplication)

            modules(
                appModule,
                recipeModule,
                databaseModule,
                networkModule,
            )
        }
    }
}