package com.example.chefia.feature.ingredientsConfirmation

sealed interface IngredientsConfirmationAction {
    data class RemoveIngredient(val ingredient: String) : IngredientsConfirmationAction
    data class ServingsChanged(val servings: Int) : IngredientsConfirmationAction
    data object AddManualIngredientClicked : IngredientsConfirmationAction
    data class ManualIngredientChanged(val value: String) : IngredientsConfirmationAction
    data object SaveManualIngredient : IngredientsConfirmationAction
    data object DismissAddIngredientSheet : IngredientsConfirmationAction
    data object Confirm : IngredientsConfirmationAction
}
