package com.example.chefia.feature.splash

data class SplashUiState(
    val progress: Float = 0f,
    val isLoadingComplete: Boolean = false,
    val isAuthenticated: Boolean = false,
)