package com.example.chefia.feature.recipe

import com.example.chefia.domain.model.Recipe

data class RecipeLoadingUiState(
    val currentIngredient: String = "Preparando...",
    val status: RecipeLoadingStatus =
        RecipeLoadingStatus.Loading,
)

sealed interface RecipeLoadingStatus {

    data object Loading : RecipeLoadingStatus

    data class Success(
        val recipes: List<Recipe>,
    ) : RecipeLoadingStatus

    data class Error(
        val message: String,
    ) : RecipeLoadingStatus
}