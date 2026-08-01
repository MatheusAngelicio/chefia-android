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
)