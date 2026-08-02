package com.example.chefia.data.remote.ai

import com.example.chefia.data.remote.ai.model.GenerateRecipesResponseDto

interface RecipeAiDataSource {

    suspend fun generateRecipes(
        ingredients: List<String>,
        isFitness: Boolean,
        isBudget: Boolean,
    ): GenerateRecipesResponseDto
}