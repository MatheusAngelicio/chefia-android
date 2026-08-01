package com.example.chefia.feature.home

sealed interface HomeEvent {

    data class ShowError(
        val message: String
    ) : HomeEvent

    data object NavigateToRecipes : HomeEvent

}