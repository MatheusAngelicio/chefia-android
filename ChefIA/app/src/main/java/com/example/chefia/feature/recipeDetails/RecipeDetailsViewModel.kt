package com.example.chefia.feature.recipeDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chefia.domain.model.Recipe
import com.example.chefia.domain.usecase.favorites.ObserveFavoriteRecipeIdsUseCase
import com.example.chefia.domain.usecase.favorites.ToggleFavoriteRecipeUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RecipeDetailsViewModel(
    private val observeFavoriteRecipeIdsUseCase: ObserveFavoriteRecipeIdsUseCase,
    private val toggleFavoriteRecipeUseCase: ToggleFavoriteRecipeUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(RecipeDetailsUiState())
    val uiState = _uiState.asStateFlow()

    fun observeFavoriteStatus(recipeId: String) {
        observeFavoriteRecipeIdsUseCase()
            .onEach { favoriteIds ->
                _uiState.update { it.copy(isFavorite = recipeId in favoriteIds) }
            }
            .launchIn(viewModelScope)
    }

    fun toggleFavorite(recipe: Recipe) {
        viewModelScope.launch {
            toggleFavoriteRecipeUseCase(recipe)
        }
    }
}
