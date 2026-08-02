package com.example.chefia

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.chefia.core.navigation.ChefIADestination
import com.example.chefia.feature.home.HomeScreen
import com.example.chefia.feature.ingredients.IngredientsScreen
import com.example.chefia.feature.recipe.RecipeGenerationScreen
import com.example.chefia.feature.splash.SplashScreen

@Composable
fun ChefIAApp() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = ChefIADestination.Splash,
    ) {
        composable<ChefIADestination.Splash> {
            SplashScreen(
                onLoadingComplete = {
                    navController.navigate(ChefIADestination.Home) {
                        popUpTo(ChefIADestination.Splash) {
                            inclusive = true
                        }
                    }
                },
            )
        }

        composable<ChefIADestination.Home> {
            HomeScreen(
                onNavigateToIngredients = {
                    navController.navigate(ChefIADestination.Ingredients)
                },
            )
        }

        composable<ChefIADestination.Ingredients> {
            IngredientsScreen(
                onBack = {
                    navController.navigateUp()
                },
                onNavigateToRecipeLoading = { ingredients ->
                    navController.navigate(
                        ChefIADestination.RecipeGeneration(
                            ingredients = ingredients,
                        ),
                    )
                },
            )
        }

        composable<ChefIADestination.RecipeGeneration> { backStackEntry ->
            val destination =
                backStackEntry.toRoute<
                        ChefIADestination.RecipeGeneration
                        >()

            RecipeGenerationScreen(
                ingredients = destination.ingredients,
                onBackClick = {
                    navController.navigateUp()
                }
            )
        }
    }
}