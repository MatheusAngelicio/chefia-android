package com.example.chefia.feature.recipeExecution

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.chefia.core.navigation.ChefIADestination
import com.example.chefia.core.navigation.RecipeNavType
import com.example.chefia.domain.model.Recipe
import kotlin.reflect.typeOf

fun NavController.navigateToRecipeExecution(
    recipe: Recipe,
    navOptions: NavOptions? = null
) {
    this.navigate(
        ChefIADestination.RecipeExecution(recipe),
        navOptions
    )
}

fun NavGraphBuilder.recipeExecutionScreen(
    onBackClick: () -> Unit,
) {
    composable<ChefIADestination.RecipeExecution>(
        typeMap = mapOf(
            typeOf<Recipe>() to RecipeNavType
        )
    ) { backStackEntry ->
        val destination = backStackEntry.toRoute<ChefIADestination.RecipeExecution>()
        RecipeExecutionScreen(
            recipe = destination.recipe,
            onBackClick = onBackClick
        )
    }
}
