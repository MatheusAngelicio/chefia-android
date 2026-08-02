package com.example.chefia.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chefia.domain.usecase.favorites.ObserveFavoriteRecipesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update

class HomeViewModel(
    private val observeFavoriteRecipesUseCase: ObserveFavoriteRecipesUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    init {
        observeFavoriteRecipesUseCase()
            .map { recipes ->
                recipes.take(5)
            }
            .onEach { recipes ->
                _uiState.update { it.copy(favoriteRecipes = recipes) }
            }
            .launchIn(viewModelScope)
    }

    fun onAction(action: HomeAction) {
        when (action) {
            HomeAction.CameraClicked -> {}
            HomeAction.TypeIngredientsClicked -> {}
            is HomeAction.RecipeClicked -> {}
            HomeAction.ViewAllFavoritesClicked -> {}
        }
    }
}