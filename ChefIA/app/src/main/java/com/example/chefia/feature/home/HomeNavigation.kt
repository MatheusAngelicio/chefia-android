package com.example.chefia.feature.home

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.chefia.core.navigation.ChefIADestination
import com.example.chefia.domain.model.Recipe

fun NavController.navigateToHome(navOptions: NavOptions? = null) {
    this.navigate(ChefIADestination.Home, navOptions)
}

fun NavGraphBuilder.homeScreen(
    onNavigateToIngredients: () -> Unit,
    onNavigateToCamera: () -> Unit,
    onRecipeClick: (Recipe) -> Unit,
    onViewAllFavoritesClick: () -> Unit,
    onLogout: () -> Unit,
) {
    composable<ChefIADestination.Home> {
        HomeScreen(
            onNavigateToIngredients = onNavigateToIngredients,
            onNavigateToCamera = onNavigateToCamera,
            onRecipeClick = onRecipeClick,
            onViewAllFavoritesClick = onViewAllFavoritesClick,
            onLogout = onLogout
        )
    }
}
