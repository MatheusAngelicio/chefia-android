package com.example.chefia.core.navigation

import com.example.chefia.domain.model.Recipe
import kotlinx.serialization.Serializable

sealed interface ChefIADestination {

    @Serializable
    data object Splash : ChefIADestination

    @Serializable
    data object Home : ChefIADestination

    @Serializable
    data object Ingredients : ChefIADestination

    @Serializable
    data class RecipeGeneration(
        val ingredients: List<String>,
        val isFitness: Boolean = false,
        val isBudget: Boolean = false,
    ) : ChefIADestination

    @Serializable
    data class RecipeDetails(
        val recipe: Recipe,
    ) : ChefIADestination
}