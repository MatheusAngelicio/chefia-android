package com.example.chefia.feature.register

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.chefia.core.navigation.ChefIADestination

fun NavController.navigateToRegister(navOptions: NavOptions? = null) {
    this.navigate(ChefIADestination.Register, navOptions)
}

fun NavGraphBuilder.registerScreen(
    onRegisterSuccess: () -> Unit,
    onBackToLogin: () -> Unit,
) {
    composable<ChefIADestination.Register> {
        RegisterScreen(
            onRegisterSuccess = onRegisterSuccess,
            onBackToLogin = onBackToLogin
        )
    }
}
