package com.example.chefia.core.navigation

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
    ) : ChefIADestination
}