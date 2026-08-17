package com.example.chefia.feature.recipeDetails

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.chefia.core.navigation.ChefIADestination
import com.example.chefia.core.navigation.RecipeNavType
import com.example.chefia.domain.model.Recipe
import kotlin.reflect.typeOf

fun NavController.navigateToRecipeDetails(
    recipe: Recipe,
    navOptions: NavOptions? = null
) {
    this.navigate(
        ChefIADestination.RecipeDetails(recipe),
        navOptions
    )
}

fun NavGraphBuilder.recipeDetailsScreen(
    onBackClick: () -> Unit,
    onStartRecipeClick: (Recipe) -> Unit,
) {
    composable<ChefIADestination.RecipeDetails>(
        typeMap = mapOf(
            typeOf<Recipe>() to RecipeNavType
        )
    ) { backStackEntry ->
        val destination = backStackEntry.toRoute<ChefIADestination.RecipeDetails>()
        RecipeDetailsScreen(
            recipe = destination.recipe,
            onBackClick = onBackClick,
            onStartRecipeClick = onStartRecipeClick
        )
    }
}
