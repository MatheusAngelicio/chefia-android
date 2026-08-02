package com.example.chefia.feature.ingredients

data class IngredientsUiState(
    val currentIngredient: String = "",
    val ingredients: List<String> = emptyList(),
    val suggestions: List<IngredientSuggestion> = defaultSuggestions,
    val isFitness: Boolean = false,
    val isBudget: Boolean = false,
    val servings: Int = 1,
) {
    val canFindRecipes: Boolean
        get() = ingredients.isNotEmpty()
}

data class IngredientSuggestion(
    val name: String,
    val emoji: String,
)

private val defaultSuggestions = listOf(
    IngredientSuggestion(
        name = "Cebola",
        emoji = "🧅",
    ),
    IngredientSuggestion(
        name = "Alho",
        emoji = "🧄",
    ),
    IngredientSuggestion(
        name = "Limão",
        emoji = "🍋",
    ),
    IngredientSuggestion(
        name = "Manteiga",
        emoji = "🧈",
    ),
)