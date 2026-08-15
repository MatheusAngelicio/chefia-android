package com.example.chefia.feature.home

import com.example.chefia.domain.model.Recipe

data class HomeUiState(
    val favoriteRecipes: List<Recipe> = emptyList(),
    val isLoading: Boolean = false,
    val isLoggedOut: Boolean = false,
    val showLogoutConfirmation: Boolean = false,
)