package com.example.chefia.feature.camera

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.chefia.core.navigation.ChefIADestination

fun NavController.navigateToCamera(navOptions: NavOptions? = null) {
    this.navigate(ChefIADestination.Camera, navOptions)
}

fun NavGraphBuilder.cameraScreen(
    onBack: () -> Unit,
    onNavigateToIngredientsConfirmation: (List<String>, String) -> Unit,
) {
    composable<ChefIADestination.Camera> {
        CameraScreen(
            onBack = onBack,
            onNavigateToIngredientsConfirmation = onNavigateToIngredientsConfirmation
        )
    }
}
