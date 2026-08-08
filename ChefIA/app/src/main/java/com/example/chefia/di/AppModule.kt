package com.example.chefia.di

import com.example.chefia.feature.camera.CameraViewModel
import com.example.chefia.feature.home.HomeViewModel
import com.example.chefia.feature.ingredients.IngredientsViewModel
import com.example.chefia.feature.splash.SplashViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    viewModelOf(::SplashViewModel)
    viewModelOf(::IngredientsViewModel)
    viewModelOf(::HomeViewModel)
    viewModelOf(::CameraViewModel)
}