package com.example.chefia.core.firebase

import android.content.Context
import com.google.firebase.FirebaseApp
import com.google.firebase.appcheck.FirebaseAppCheck
import com.google.firebase.appcheck.debug.DebugAppCheckProviderFactory

object AppCheckInitializer {

    fun initialize(context: Context) {
        FirebaseApp.initializeApp(context)

        FirebaseAppCheck
            .getInstance()
            .installAppCheckProviderFactory(
                DebugAppCheckProviderFactory.getInstance(),
            )
    }
}