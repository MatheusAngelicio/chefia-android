package com.example.chefia.di

import com.example.chefia.core.common.extensions.deleteTempPhoto
import com.example.chefia.feature.camera.CameraViewModel
import com.example.chefia.feature.home.HomeViewModel
import com.example.chefia.feature.ingredients.IngredientsViewModel
import com.example.chefia.feature.ingredientsConfirmation.IngredientsConfirmationViewModel
import com.example.chefia.feature.splash.SplashViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    viewModelOf(::SplashViewModel)
    viewModelOf(::IngredientsViewModel)
    viewModelOf(::HomeViewModel)
    viewModelOf(::CameraViewModel)

    viewModel { params ->
        IngredientsConfirmationViewModel(
            ingredients = params.get(),
            photoPath = params.get(),
            onClearPhoto = { deleteTempPhoto(androidContext()) }
        )
    }
}
