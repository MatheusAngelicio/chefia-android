package com.example.chefia.feature.login

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.chefia.core.navigation.ChefIADestination

fun NavController.navigateToLogin(navOptions: NavOptions? = null) {
    this.navigate(ChefIADestination.Login, navOptions)
}

fun NavGraphBuilder.loginScreen(
    onLoginSuccess: () -> Unit,
    onNavigateToRegister: () -> Unit,
) {
    composable<ChefIADestination.Login> {
        LoginScreen(
            onLoginSuccess = onLoginSuccess,
            onNavigateToRegister = onNavigateToRegister
        )
    }
}
