package com.example.chefia.feature.home

data class HomeUiState(
    val ingredients: List<String> = emptyList(),
    val currentIngredient: String = "",
    val isLoading: Boolean = false
)