package com.example.chefia.feature.recipeExecution

import androidx.lifecycle.ViewModel
import com.example.chefia.domain.model.Recipe
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class RecipeExecutionViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(RecipeExecutionUiState())
    val uiState = _uiState.asStateFlow()

    fun init(recipe: Recipe) {
        _uiState.update { it.copy(recipe = recipe) }
    }

    fun onAction(action: RecipeExecutionAction) {
        when (action) {
            RecipeExecutionAction.NextStep -> {
                _uiState.update { currentState ->
                    if (currentState.currentStepIndex < currentState.totalSteps - 1) {
                        currentState.copy(currentStepIndex = currentState.currentStepIndex + 1)
                    } else {
                        currentState
                    }
                }
            }

            RecipeExecutionAction.PreviousStep -> {
                _uiState.update { currentState ->
                    if (currentState.currentStepIndex > 0) {
                        currentState.copy(currentStepIndex = currentState.currentStepIndex - 1)
                    } else {
                        currentState
                    }
                }
            }

            RecipeExecutionAction.FinishRecipe -> {
                // Implementation for finishing recipe can be handled by navigation or specific logic
            }
        }
    }
}
