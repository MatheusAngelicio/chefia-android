package com.example.chefia.data.remote.ai.model

import kotlinx.serialization.Serializable

@Serializable
data class GenerateRecipesResponseDto(
    val recipes: List<RecipeDto>,
)

@Serializable
data class RecipeDto(
    val name: String,
    val description: String,
    val preparationTimeMinutes: Int,
    val servings: Int,
    val caloriesPerServingKcal: Int,
    val difficulty: String,
    val ingredients: List<RecipeIngredientDto>,
    val preparationSteps: List<String>,
)

@Serializable
data class RecipeIngredientDto(
    val name: String,
    val quantity: String,
    val isAvailable: Boolean,
)