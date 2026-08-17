package com.example.chefia.feature.recipeGeneration

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.chefia.core.navigation.ChefIADestination
import com.example.chefia.domain.model.Recipe

fun NavController.navigateToRecipeGeneration(
    ingredients: List<String>,
    servings: Int,
    navOptions: NavOptions? = null
) {
    this.navigate(
        ChefIADestination.RecipeGeneration(ingredients, servings),
        navOptions
    )
}

fun NavGraphBuilder.recipeGenerationScreen(
    onBackClick: () -> Unit,
    onRecipeClick: (Recipe) -> Unit,
) {
    composable<ChefIADestination.RecipeGeneration> { backStackEntry ->
        val destination = backStackEntry.toRoute<ChefIADestination.RecipeGeneration>()
        RecipeGenerationScreen(
            ingredients = destination.ingredients,
            servings = destination.servings,
            onBackClick = onBackClick,
            onRecipeClick = onRecipeClick
        )
    }
}
