package com.example.chefia.feature.splash

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.chefia.core.navigation.ChefIADestination

fun NavController.navigateToSplash(navOptions: NavOptions? = null) {
    this.navigate(ChefIADestination.Splash, navOptions)
}

fun NavGraphBuilder.splashScreen(
    onLoadingComplete: (Boolean) -> Unit,
) {
    composable<ChefIADestination.Splash> {
        SplashScreen(onLoadingComplete = onLoadingComplete)
    }
}
