package com.example.chefia

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.chefia.core.navigation.ChefIADestination
import com.example.chefia.core.navigation.RecipeNavType
import com.example.chefia.domain.model.Recipe
import com.example.chefia.feature.camera.CameraScreen
import com.example.chefia.feature.home.HomeScreen
import com.example.chefia.feature.ingredients.IngredientsScreen
import com.example.chefia.feature.ingredientsConfirmation.IngredientsConfirmationScreen
import com.example.chefia.feature.recipeGeneration.RecipeGenerationScreen
import com.example.chefia.feature.recipeDetails.RecipeDetailsScreen
import com.example.chefia.feature.recipeExecution.RecipeExecutionScreen
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
                onNavigateToCamera = {
                    navController.navigate(ChefIADestination.Camera)
                },
                onRecipeClick = { recipe ->
                    navController.navigate(ChefIADestination.RecipeDetails(recipe))
                }
            )
        }

        composable<ChefIADestination.Camera> {
            CameraScreen(
                onBack = {
                    navController.navigateUp()
                },
                onNavigateToIngredientsConfirmation = { ingredients, photoPath ->
                    navController.navigate(
                        ChefIADestination.IngredientsConfirmation(ingredients, photoPath)
                    ) {
                        popUpTo(ChefIADestination.Camera) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable<ChefIADestination.IngredientsConfirmation> { backStackEntry ->
            val destination = backStackEntry.toRoute<ChefIADestination.IngredientsConfirmation>()
            IngredientsConfirmationScreen(
                ingredients = destination.ingredients,
                photoPath = destination.photoPath,
                onBackClick = {
                    navController.navigateUp()
                },
                onAddManualClick = {
                    navController.navigate(ChefIADestination.Ingredients)
                },
                onConfirmClick = { ingredients, servings ->
                    navController.navigate(
                        ChefIADestination.RecipeGeneration(
                            ingredients = ingredients,
                            servings = servings
                        )
                    )
                }
            )
        }

        composable<ChefIADestination.Ingredients> {
            IngredientsScreen(
                onBack = {
                    navController.navigateUp()
                },
                onNavigateToRecipeGeneration = {
                        ingredients,
                        servings
                    ->
                    navController.navigate(
                        ChefIADestination.RecipeGeneration(
                            ingredients = ingredients,
                            servings = servings,
                        ),
                    )
                },
            )
        }

        composable<ChefIADestination.RecipeGeneration> { backStackEntry ->
            val destination =
                backStackEntry.toRoute<ChefIADestination.RecipeGeneration>()

            RecipeGenerationScreen(
                ingredients = destination.ingredients,
                servings = destination.servings,
                onBackClick = {
                    navController.navigateUp()
                },
                onRecipeClick = { recipe ->
                    navController.navigate(ChefIADestination.RecipeDetails(recipe))
                }
            )
        }

        composable<ChefIADestination.RecipeDetails>(
            typeMap = mapOf(
                kotlin.reflect.typeOf<Recipe>() to RecipeNavType
            )
        ) { backStackEntry ->
            val destination =
                backStackEntry.toRoute<
                        ChefIADestination.RecipeDetails
                        >()

            RecipeDetailsScreen(
                recipe = destination.recipe,
                onBackClick = {
                    navController.navigateUp()
                },
                onStartRecipeClick = { recipe ->
                    navController.navigate(ChefIADestination.RecipeExecution(recipe))
                }
            )
        }

        composable<ChefIADestination.RecipeExecution>(
            typeMap = mapOf(
                kotlin.reflect.typeOf<Recipe>() to RecipeNavType
            )
        ) { backStackEntry ->
            val destination =
                backStackEntry.toRoute<ChefIADestination.RecipeExecution>()

            RecipeExecutionScreen(
                recipe = destination.recipe,
                onBackClick = {
                    navController.navigateUp()
                }
            )
        }
    }
}