package com.example.chefia.core.firebase

import android.content.Context
import com.google.firebase.FirebaseApp

object AppCheckInitializer {

    fun initialize(context: Context) {
        FirebaseApp.initializeApp(context)
    }
}