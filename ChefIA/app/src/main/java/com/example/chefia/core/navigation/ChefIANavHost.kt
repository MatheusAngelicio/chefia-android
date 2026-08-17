package com.example.chefia.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import androidx.navigation.navOptions
import com.example.chefia.ChefIAAppState
import com.example.chefia.feature.camera.cameraScreen
import com.example.chefia.feature.camera.navigateToCamera
import com.example.chefia.feature.favorites.favoritesScreen
import com.example.chefia.feature.favorites.navigateToFavorites
import com.example.chefia.feature.home.homeScreen
import com.example.chefia.feature.home.navigateToHome
import com.example.chefia.feature.ingredients.ingredientsScreen
import com.example.chefia.feature.ingredients.navigateToIngredients
import com.example.chefia.feature.ingredientsConfirmation.ingredientsConfirmationScreen
import com.example.chefia.feature.ingredientsConfirmation.navigateToIngredientsConfirmation
import com.example.chefia.feature.login.loginScreen
import com.example.chefia.feature.login.navigateToLogin
import com.example.chefia.feature.recipeDetails.navigateToRecipeDetails
import com.example.chefia.feature.recipeDetails.recipeDetailsScreen
import com.example.chefia.feature.recipeExecution.navigateToRecipeExecution
import com.example.chefia.feature.recipeExecution.recipeExecutionScreen
import com.example.chefia.feature.recipeGeneration.navigateToRecipeGeneration
import com.example.chefia.feature.recipeGeneration.recipeGenerationScreen
import com.example.chefia.feature.register.navigateToRegister
import com.example.chefia.feature.register.registerScreen
import com.example.chefia.feature.splash.splashScreen

@Composable
fun ChefIANavHost(
    appState: ChefIAAppState,
    modifier: Modifier = Modifier,
    startDestination: ChefIADestination = ChefIADestination.Splash,
) {
    val navController = appState.navController

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        splashScreen(
            onLoadingComplete = { isAuthenticated ->
                val destination = if (isAuthenticated) {
                    ChefIADestination.Home
                } else {
                    ChefIADestination.Login
                }

                navController.navigate(destination) {
                    popUpTo(ChefIADestination.Splash) {
                        inclusive = true
                    }
                }
            },
        )

        loginScreen(
            onLoginSuccess = {
                navController.navigateToHome(
                    navOptions = navOptions {
                        popUpTo(ChefIADestination.Login) {
                            inclusive = true
                        }
                    }
                )
            },
            onNavigateToRegister = {
                navController.navigateToRegister()
            }
        )

        registerScreen(
            onRegisterSuccess = {
                navController.navigateToHome(
                    navOptions = navOptions {
                        popUpTo(ChefIADestination.Login) {
                            inclusive = true
                        }
                    }
                )
            },
            onBackToLogin = {
                appState.navigateUp()
            }
        )

        homeScreen(
            onNavigateToIngredients = {
                navController.navigateToIngredients()
            },
            onNavigateToCamera = {
                navController.navigateToCamera()
            },
            onRecipeClick = { recipe ->
                navController.navigateToRecipeDetails(recipe)
            },
            onViewAllFavoritesClick = {
                navController.navigateToFavorites()
            },
            onLogout = {
                navController.navigateToLogin(
                    navOptions = navOptions {
                        popUpTo(ChefIADestination.Home) {
                            inclusive = true
                        }
                    }
                )
            }
        )

        favoritesScreen(
            onBackClick = {
                appState.navigateUp()
            },
            onExploreClick = {
                navController.navigateToHome(
                    navOptions = navOptions {
                        popUpTo(ChefIADestination.Home) {
                            inclusive = true
                        }
                    }
                )
            },
            onRecipeClick = { recipe ->
                navController.navigateToRecipeDetails(recipe)
            }
        )

        cameraScreen(
            onBack = {
                appState.navigateUp()
            },
            onNavigateToIngredientsConfirmation = { ingredients, photoPath ->
                navController.navigateToIngredientsConfirmation(
                    ingredients = ingredients,
                    photoPath = photoPath,
                    navOptions = navOptions {
                        popUpTo(ChefIADestination.Camera) {
                            inclusive = true
                        }
                    }
                )
            }
        )

        ingredientsConfirmationScreen(
            onBackClick = {
                appState.navigateUp()
            },
            onConfirmClick = { ingredients, servings ->
                navController.navigateToRecipeGeneration(
                    ingredients = ingredients,
                    servings = servings
                )
            }
        )

        ingredientsScreen(
            onBack = {
                appState.navigateUp()
            },
            onNavigateToRecipeGeneration = { ingredients, servings ->
                navController.navigateToRecipeGeneration(
                    ingredients = ingredients,
                    servings = servings
                )
            }
        )

        recipeGenerationScreen(
            onBackClick = {
                appState.navigateUp()
            },
            onRecipeClick = { recipe ->
                navController.navigateToRecipeDetails(recipe)
            }
        )

        recipeDetailsScreen(
            onBackClick = {
                appState.navigateUp()
            },
            onStartRecipeClick = { recipe ->
                navController.navigateToRecipeExecution(recipe)
            }
        )

        recipeExecutionScreen(
            onBackClick = {
                appState.navigateUp()
            }
        )
    }
}
