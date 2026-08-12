package com.example.chefia.feature.favorites

import com.example.chefia.domain.model.Recipe

data class FavoritesUiState(
    val recipes: List<Recipe> = emptyList(),
    val filteredRecipes: List<Recipe> = emptyList(),
    val searchQuery: String = "",
    val isLoading: Boolean = false,
)
