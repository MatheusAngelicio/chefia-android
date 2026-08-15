package com.example.chefia.core.navigation

import com.example.chefia.domain.model.Recipe
import kotlinx.serialization.Serializable

sealed interface ChefIADestination {

    @Serializable
    data object Splash : ChefIADestination

    @Serializable
    data object Login : ChefIADestination

    @Serializable
    data object Register : ChefIADestination

    @Serializable
    data object Home : ChefIADestination

    @Serializable
    data object Camera : ChefIADestination

    @Serializable
    data object Ingredients : ChefIADestination

    @Serializable
    data class IngredientsConfirmation(
        val ingredients: List<String>,
        val photoPath: String,
    ) : ChefIADestination

    @Serializable
    data class RecipeGeneration(
        val ingredients: List<String>,
        val servings: Int,
    ) : ChefIADestination

    @Serializable
    data class RecipeDetails(
        val recipe: Recipe,
    ) : ChefIADestination

    @Serializable
    data class RecipeExecution(
        val recipe: Recipe,
    ) : ChefIADestination

    @Serializable
    data object Favorites : ChefIADestination
}