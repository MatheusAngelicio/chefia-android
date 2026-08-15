package com.example.chefia.feature.home

import com.example.chefia.domain.model.Recipe

sealed interface HomeAction {

    data object CameraClicked : HomeAction
    data object TypeIngredientsClicked : HomeAction
    data class RecipeClicked(val recipe: Recipe) : HomeAction
    data object ViewAllFavoritesClicked : HomeAction
    data object LogoutRequest : HomeAction
    data object LogoutConfirm : HomeAction
    data object LogoutDismiss : HomeAction
}