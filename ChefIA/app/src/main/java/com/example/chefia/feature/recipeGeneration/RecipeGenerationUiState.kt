package com.example.chefia.feature.recipeGeneration

import com.example.chefia.core.common.UiText
import com.example.chefia.domain.model.Recipe

data class RecipeGenerationUiState(
    val currentIngredient: String = "Preparando...",
    val favoriteRecipeIds: Set<String> = emptySet(),
    val status: RecipeGenerationStatus =
        RecipeGenerationStatus.Loading,
)

sealed interface RecipeGenerationStatus {

    data object Loading : RecipeGenerationStatus

    data class Success(
        val recipes: List<Recipe>,
    ) : RecipeGenerationStatus

    data class Error(
        val message: UiText,
    ) : RecipeGenerationStatus
}