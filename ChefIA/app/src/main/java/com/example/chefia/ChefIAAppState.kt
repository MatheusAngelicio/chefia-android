package com.example.chefia

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Stable
class ChefIAAppState(
    val navController: NavHostController,
) {
    fun navigateUp() {
        navController.navigateUp()
    }
}

@Composable
fun rememberChefIAAppState(
    navController: NavHostController = rememberNavController(),
): ChefIAAppState {
    return remember(navController) {
        ChefIAAppState(navController)
    }
}
