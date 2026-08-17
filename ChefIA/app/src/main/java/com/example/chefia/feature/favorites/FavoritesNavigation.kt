package com.example.chefia.feature.favorites

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.chefia.core.navigation.ChefIADestination
import com.example.chefia.domain.model.Recipe

fun NavController.navigateToFavorites(navOptions: NavOptions? = null) {
    this.navigate(ChefIADestination.Favorites, navOptions)
}

fun NavGraphBuilder.favoritesScreen(
    onBackClick: () -> Unit,
    onExploreClick: () -> Unit,
    onRecipeClick: (Recipe) -> Unit,
) {
    composable<ChefIADestination.Favorites> {
        FavoritesScreen(
            onBackClick = onBackClick,
            onExploreClick = onExploreClick,
            onRecipeClick = onRecipeClick
        )
    }
}
