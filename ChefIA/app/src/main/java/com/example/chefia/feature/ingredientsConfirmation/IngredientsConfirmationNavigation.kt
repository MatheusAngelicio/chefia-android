package com.example.chefia.feature.ingredientsConfirmation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.chefia.core.navigation.ChefIADestination

fun NavController.navigateToIngredientsConfirmation(
    ingredients: List<String>,
    photoPath: String,
    navOptions: NavOptions? = null
) {
    this.navigate(
        ChefIADestination.IngredientsConfirmation(ingredients, photoPath),
        navOptions
    )
}

fun NavGraphBuilder.ingredientsConfirmationScreen(
    onBackClick: () -> Unit,
    onConfirmClick: (List<String>, Int) -> Unit,
) {
    composable<ChefIADestination.IngredientsConfirmation> { backStackEntry ->
        val destination = backStackEntry.toRoute<ChefIADestination.IngredientsConfirmation>()
        IngredientsConfirmationScreen(
            ingredients = destination.ingredients,
            photoPath = destination.photoPath,
            onBackClick = onBackClick,
            onConfirmClick = onConfirmClick
        )
    }
}
