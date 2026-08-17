package com.example.chefia.feature.ingredients

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.chefia.core.navigation.ChefIADestination

fun NavController.navigateToIngredients(navOptions: NavOptions? = null) {
    this.navigate(ChefIADestination.Ingredients, navOptions)
}

fun NavGraphBuilder.ingredientsScreen(
    onBack: () -> Unit,
    onNavigateToRecipeGeneration: (List<String>, Int) -> Unit,
) {
    composable<ChefIADestination.Ingredients> {
        IngredientsScreen(
            onBack = onBack,
            onNavigateToRecipeGeneration = onNavigateToRecipeGeneration
        )
    }
}
