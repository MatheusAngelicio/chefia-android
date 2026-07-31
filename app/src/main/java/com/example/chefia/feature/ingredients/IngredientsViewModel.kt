package com.example.chefia.feature.ingredients

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class IngredientsViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(IngredientsUiState())
    val uiState = _uiState.asStateFlow()

    fun onAction(action: IngredientsAction) {
        when (action) {
            is IngredientsAction.IngredientChanged -> {
                updateCurrentIngredient(action.value)
            }

            IngredientsAction.AddIngredient -> {
                addCurrentIngredient()
            }

            is IngredientsAction.SuggestionClicked -> {
                addIngredient(action.ingredient)
            }

            is IngredientsAction.RemoveIngredient -> {
                removeIngredient(action.ingredient)
            }

            IngredientsAction.FindRecipesClicked -> Unit
        }
    }

    private fun updateCurrentIngredient(value: String) {
        _uiState.update { currentState ->
            currentState.copy(
                currentIngredient = value,
            )
        }
    }

    private fun addCurrentIngredient() {
        val ingredient = _uiState.value.currentIngredient.trim()

        if (ingredient.isBlank()) {
            return
        }

        addIngredient(ingredient)

        _uiState.update { currentState ->
            currentState.copy(
                currentIngredient = "",
            )
        }
    }

    private fun addIngredient(ingredient: String) {
        _uiState.update { currentState ->
            val alreadyExists = currentState.ingredients.any {
                it.equals(
                    other = ingredient,
                    ignoreCase = true,
                )
            }

            if (alreadyExists) {
                currentState
            } else {
                currentState.copy(
                    ingredients = currentState.ingredients + ingredient,
                )
            }
        }
    }

    private fun removeIngredient(ingredient: String) {
        _uiState.update { currentState ->
            currentState.copy(
                ingredients = currentState.ingredients - ingredient,
            )
        }
    }
}