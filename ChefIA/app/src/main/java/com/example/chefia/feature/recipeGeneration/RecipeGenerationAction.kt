package com.example.chefia.feature.recipeGeneration

import com.example.chefia.domain.model.Recipe

sealed interface RecipeGenerationAction {

    data class GenerateRecipes(
        val ingredients: List<String>,
        val servings: Int,
    ) : RecipeGenerationAction

    data object RetryClicked : RecipeGenerationAction

    data class FavoriteClicked(
        val recipe: Recipe,
    ) : RecipeGenerationAction

    data class RecipeClicked(
        val recipe: Recipe,
    ) : RecipeGenerationAction
}