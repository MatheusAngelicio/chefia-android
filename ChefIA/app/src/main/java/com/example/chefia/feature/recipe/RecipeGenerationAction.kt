package com.example.chefia.feature.recipe

sealed interface RecipeGenerationAction {

    data class GenerateRecipes(
        val ingredients: List<String>,
    ) : RecipeGenerationAction

    data object RetryClicked : RecipeGenerationAction
}