package com.example.chefia.feature.recipe

import com.example.chefia.domain.model.Recipe

sealed interface RecipeGenerationAction {

    data class GenerateRecipes(
        val ingredients: List<String>,
    ) : RecipeGenerationAction

    data object RetryClicked : RecipeGenerationAction

    data class FavoriteClicked(
        val recipe: Recipe,
    ) : RecipeGenerationAction

    data class RecipeClicked(
        val recipeId: String,
    ) : RecipeGenerationAction
}