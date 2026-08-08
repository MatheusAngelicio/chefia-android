package com.example.chefia.feature.ingredientsConfirmation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class IngredientsConfirmationViewModel(
    ingredients: List<String>,
    photoPath: String,
    private val onClearPhoto: () -> Unit,
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        IngredientsConfirmationUiState(
            ingredients = ingredients,
            photoPath = photoPath
        )
    )
    val uiState = _uiState.asStateFlow()

    fun onAction(action: IngredientsConfirmationAction) {
        when (action) {
            is IngredientsConfirmationAction.RemoveIngredient -> {
                _uiState.update { it.copy(ingredients = it.ingredients - action.ingredient) }
            }
            is IngredientsConfirmationAction.ServingsChanged -> {
                _uiState.update { it.copy(servings = action.servings) }
            }
            IngredientsConfirmationAction.AddManualIngredient -> {
                // To be handled by navigation
            }
            IngredientsConfirmationAction.Confirm -> {
                // To be handled by navigation
            }
        }
    }

    override fun onCleared() {
        onClearPhoto()
    }
}
