package com.example.chefia.feature.home

sealed interface HomeAction {

    data object CameraClicked : HomeAction
    data object TypeIngredientsClicked : HomeAction
}