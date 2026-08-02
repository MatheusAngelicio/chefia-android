package com.example.chefia.feature.recipeGeneration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chefia.domain.model.Recipe
import com.example.chefia.domain.usecase.GenerateRecipesUseCase
import com.example.chefia.domain.usecase.favorites.ObserveFavoriteRecipeIdsUseCase
import com.example.chefia.domain.usecase.favorites.ToggleFavoriteRecipeUseCase
import com.example.chefia.feature.recipeGeneration.mapper.RecipeErrorMapper
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RecipeGenerationViewModel(
    private val generateRecipesUseCase: GenerateRecipesUseCase,
    private val observeFavoriteRecipeIdsUseCase: ObserveFavoriteRecipeIdsUseCase,
    private val toggleFavoriteRecipeUseCase: ToggleFavoriteRecipeUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        RecipeGenerationUiState(),
    )

    val uiState = _uiState.asStateFlow()

    private var generationJob: Job? = null

    private var ingredients: List<String> = emptyList()
    private var servings: Int = 1
    private var isFitness: Boolean = false
    private var isBudget: Boolean = false

    init {
        observeFavoriteRecipeIdsUseCase()
            .onEach { favoriteIds ->
                _uiState.update { it.copy(favoriteRecipeIds = favoriteIds) }
            }
            .launchIn(viewModelScope)
    }

    fun onAction(action: RecipeGenerationAction) {
        when (action) {
            is RecipeGenerationAction.GenerateRecipes -> {
                ingredients = action.ingredients
                servings = action.servings
                isFitness = action.isFitness
                isBudget = action.isBudget
                generateRecipes()
            }

            RecipeGenerationAction.RetryClicked -> {
                retry()
            }

            is RecipeGenerationAction.FavoriteClicked -> {
                toggleFavorite(action.recipe)
            }

            is RecipeGenerationAction.RecipeClicked -> Unit
        }
    }

    private fun generateRecipes() {
        if (generationJob?.isActive == true) {
            return
        }

        if (_uiState.value.status is RecipeGenerationStatus.Success) {
            return
        }

        generationJob = viewModelScope.launch {
            updateLoadingState()

            generateRecipesUseCase(
                ingredients = ingredients,
                servings = servings,
                isFitness = isFitness,
                isBudget = isBudget,
            )
                .onEach { recipes ->
                    _uiState.update { currentState ->
                        currentState.copy(
                            status = RecipeGenerationStatus.Success(
                                recipes = recipes,
                            ),
                        )
                    }
                }
                .catch { error ->
                    _uiState.update { currentState ->
                        currentState.copy(
                            status = RecipeGenerationStatus.Error(
                                message = RecipeErrorMapper.map(error),
                            ),
                        )
                    }
                }
                .launchIn(this)
        }
    }

    private fun retry() {
        _uiState.update { currentState ->
            currentState.copy(
                status = RecipeGenerationStatus.Loading,
            )
        }

        generateRecipes()
    }

    private fun updateLoadingState() {
        val firstIngredient = ingredients
            .firstOrNull()
            ?.replaceFirstChar(Char::uppercase)
            ?: "Ingredientes"

        _uiState.update { currentState ->
            currentState.copy(
                currentIngredient = "$firstIngredient...",
                status = RecipeGenerationStatus.Loading,
            )
        }
    }

    private fun toggleFavorite(recipe: Recipe) {
        viewModelScope.launch {
            toggleFavoriteRecipeUseCase(recipe)
        }
    }
}