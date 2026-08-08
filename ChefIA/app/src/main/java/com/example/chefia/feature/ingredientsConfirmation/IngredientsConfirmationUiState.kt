package com.example.chefia.feature.ingredientsConfirmation

data class IngredientsConfirmationUiState(
    val ingredients: List<String> = emptyList(),
    val photoPath: String = "",
    val servings: Int = 1,
    val isAddIngredientSheetOpen: Boolean = false,
    val manualIngredientInput: String = ""
)
