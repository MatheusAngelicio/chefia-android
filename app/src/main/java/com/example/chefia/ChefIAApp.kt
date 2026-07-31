package com.example.chefia

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.chefia.core.navigation.ChefIADestination
import com.example.chefia.feature.home.HomeScreen
import com.example.chefia.feature.ingredients.IngredientsScreen

@Composable
fun ChefIAApp() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = ChefIADestination.Home,
    ) {
        composable<ChefIADestination.Home> {
            HomeScreen(
                onNavigateToIngredients = {
                    navController.navigate(ChefIADestination.Ingredients)
                },
            )
        }

        composable<ChefIADestination.Ingredients> {
            IngredientsScreen()
        }
    }
}