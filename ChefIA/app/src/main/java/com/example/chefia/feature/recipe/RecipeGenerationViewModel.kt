package com.example.chefia.feature.recipe

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chefia.domain.usecase.GenerateRecipesUseCase
import com.example.chefia.feature.recipe.mapper.RecipeErrorMapper
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RecipeGenerationViewModel(
    private val generateRecipesUseCase: GenerateRecipesUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        RecipeGenerationUiState(),
    )

    val uiState = _uiState.asStateFlow()

    private var generationJob: Job? = null

    private var ingredients: List<String> = emptyList()

    fun onAction(action: RecipeGenerationAction) {
        when (action) {
            is RecipeGenerationAction.GenerateRecipes -> {
                ingredients = action.ingredients
                generateRecipes()
            }

            RecipeGenerationAction.RetryClicked -> {
                retry()
            }
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

            runCatching {
                generateRecipesUseCase(ingredients)
            }.onSuccess { recipes ->
                _uiState.update { currentState ->
                    currentState.copy(
                        status = RecipeGenerationStatus.Success(
                            recipes = recipes,
                        ),
                    )
                }
            }.onFailure { error ->
                _uiState.update { currentState ->
                    currentState.copy(
                        status = RecipeGenerationStatus.Error(
                            message = RecipeErrorMapper.map(error),
                        ),
                    )
                }
            }
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
}