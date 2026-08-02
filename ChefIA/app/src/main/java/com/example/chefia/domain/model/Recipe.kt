package com.example.chefia.domain.model

data class Recipe(
    val id: String,
    val name: String,
    val description: String,
    val preparationTimeMinutes: Int,
    val servings: Int,
    val difficulty: RecipeDifficulty,
    val ingredients: List<RecipeIngredient>,
    val preparationSteps: List<String>,
    val imageUrl: String? = null,
) {
    val availableIngredientsCount: Int
        get() = ingredients.count { it.isAvailable }

    val totalIngredientsCount: Int
        get() = ingredients.size

    val compatibilityPercentage: Int
        get() {
            if (totalIngredientsCount == 0) {
                return 0
            }

            return (
                    availableIngredientsCount
                        .toFloat()
                        .div(totalIngredientsCount)
                        .times(100)
                    ).toInt()
        }

    val difficultyToDisplayName: String
        get() {
            return when (difficulty) {
                RecipeDifficulty.EASY -> "Fácil"
                RecipeDifficulty.MEDIUM -> "Média"
                RecipeDifficulty.HARD -> "Difícil"
            }
        }
}