package com.example.chefia.feature.ingredients

sealed interface IngredientsAction {

    data class IngredientChanged(
        val value: String,
    ) : IngredientsAction

    data object AddIngredient : IngredientsAction

    data class SuggestionClicked(
        val ingredient: String,
    ) : IngredientsAction

    data class RemoveIngredient(
        val ingredient: String,
    ) : IngredientsAction

    data class FitnessToggled(
        val isChecked: Boolean,
    ) : IngredientsAction

    data class BudgetToggled(
        val isChecked: Boolean,
    ) : IngredientsAction

    data class ServingsChanged(
        val servings: Int,
    ) : IngredientsAction

    data object FindRecipesClicked : IngredientsAction
}