package com.example.chefia.data.mapper

import com.example.chefia.data.remote.ai.model.GenerateRecipesResponseDto
import com.example.chefia.domain.model.Recipe
import com.example.chefia.domain.model.RecipeDifficulty
import com.example.chefia.domain.model.RecipeIngredient
import java.util.Locale
import java.util.UUID

fun GenerateRecipesResponseDto.toDomain(): List<Recipe> {
    return recipes.map { recipeDto ->
        Recipe(
            id = UUID.randomUUID().toString(),
            name = recipeDto.name,
            description = recipeDto.description,
            preparationTimeMinutes =
                recipeDto.preparationTimeMinutes,
            servings = recipeDto.servings,
            caloriesPerServingKcal = recipeDto.caloriesPerServingKcal,
            difficulty =
                recipeDto.difficulty.toDomainDifficulty(),
            ingredients = recipeDto.ingredients.map { ingredientDto ->
                RecipeIngredient(
                    name = ingredientDto.name,
                    quantity = ingredientDto.quantity,
                    isAvailable = ingredientDto.isAvailable,
                )
            },
            preparationSteps =
                recipeDto.preparationSteps,
        )
    }
}

private fun String.toDomainDifficulty(): RecipeDifficulty {
    return when (lowercase(Locale.ROOT)) {
        "fácil",
        "facil",
            -> RecipeDifficulty.EASY

        "média",
        "media",
            -> RecipeDifficulty.MEDIUM

        "difícil",
        "dificil",
            -> RecipeDifficulty.HARD

        else -> RecipeDifficulty.MEDIUM
    }
}