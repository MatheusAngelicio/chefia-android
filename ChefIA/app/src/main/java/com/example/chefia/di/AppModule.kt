package com.example.chefia.di

import com.example.chefia.core.common.extensions.deleteTempPhoto
import com.example.chefia.data.repository.AuthRepositoryImpl
import com.example.chefia.domain.repository.AuthRepository
import com.example.chefia.feature.camera.CameraViewModel
import com.example.chefia.feature.favorites.FavoritesViewModel
import com.example.chefia.feature.home.HomeViewModel
import com.example.chefia.feature.ingredients.IngredientsViewModel
import com.example.chefia.feature.ingredientsConfirmation.IngredientsConfirmationViewModel
import com.example.chefia.feature.login.LoginViewModel
import com.example.chefia.feature.login.util.GoogleAuthUiClient
import com.example.chefia.feature.register.RegisterViewModel
import com.example.chefia.feature.splash.SplashViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    single { Firebase.auth }
    single<AuthRepository> { AuthRepositoryImpl(get()) }
    single { GoogleAuthUiClient(androidContext()) }

    viewModelOf(::SplashViewModel)
    viewModelOf(::LoginViewModel)
    viewModelOf(::RegisterViewModel)
    viewModelOf(::IngredientsViewModel)
    viewModelOf(::HomeViewModel)
    viewModelOf(::CameraViewModel)
    viewModelOf(::FavoritesViewModel)

    viewModel { params ->
        IngredientsConfirmationViewModel(
            ingredients = params.get(),
            photoPath = params.get(),
            onClearPhoto = { path -> deleteTempPhoto(path) }
        )
    }
}
