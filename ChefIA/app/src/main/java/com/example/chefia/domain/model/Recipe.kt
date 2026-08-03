package com.example.chefia.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Recipe(
    val id: String,
    val name: String,
    val description: String,
    val preparationTimeMinutes: Int,
    val servings: Int,
    val caloriesPerServingKcal: Int,
    val difficulty: RecipeDifficulty,
    val ingredients: List<RecipeIngredient>,
    val preparationSteps: List<RecipeStep>,
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
        get() = when (difficulty) {
            RecipeDifficulty.EASY -> "Fácil"
            RecipeDifficulty.MEDIUM -> "Média"
            RecipeDifficulty.HARD -> "Difícil"
        }

    val caloriesPerServingDisplay: String
        get() = "$caloriesPerServingKcal kcal/porção"

    val servingsToDisplay: String
        get() = if (servings == 1) {
            "1 porção"
        } else {
            "$servings porções"
        }

}