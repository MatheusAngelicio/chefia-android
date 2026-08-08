package com.example.chefia.feature.ingredientsConfirmation

sealed interface IngredientsConfirmationAction {
    data class RemoveIngredient(val ingredient: String) : IngredientsConfirmationAction
    data class ServingsChanged(val servings: Int) : IngredientsConfirmationAction
    data object AddManualIngredient : IngredientsConfirmationAction
    data object Confirm : IngredientsConfirmationAction
}
