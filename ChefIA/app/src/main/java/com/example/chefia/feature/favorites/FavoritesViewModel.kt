package com.example.chefia.feature.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chefia.domain.model.Recipe
import com.example.chefia.domain.usecase.favorites.ObserveFavoriteRecipesUseCase
import com.example.chefia.domain.usecase.favorites.ToggleFavoriteRecipeUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FavoritesViewModel(
    private val observeFavoriteRecipesUseCase: ObserveFavoriteRecipesUseCase,
    private val toggleFavoriteRecipeUseCase: ToggleFavoriteRecipeUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(FavoritesUiState())
    val uiState = _uiState.asStateFlow()

    init {
        observeFavoriteRecipesUseCase()
            .onEach { recipes ->
                _uiState.update { 
                    it.copy(
                        recipes = recipes,
                        filteredRecipes = filterRecipes(recipes, it.searchQuery)
                    ) 
                }
            }
            .launchIn(viewModelScope)
    }

    fun onAction(action: FavoritesAction) {
        when (action) {
            is FavoritesAction.SearchQueryChanged -> {
                _uiState.update { 
                    it.copy(
                        searchQuery = action.query,
                        filteredRecipes = filterRecipes(it.recipes, action.query)
                    )
                }
            }
            is FavoritesAction.ToggleFavorite -> {
                viewModelScope.launch {
                    toggleFavoriteRecipeUseCase(action.recipe)
                }
            }
            else -> {}
        }
    }

    private fun filterRecipes(recipes: List<Recipe>, query: String): List<Recipe> {
        if (query.isBlank()) return recipes
        return recipes.filter { it.name.contains(query, ignoreCase = true) }
    }
}
