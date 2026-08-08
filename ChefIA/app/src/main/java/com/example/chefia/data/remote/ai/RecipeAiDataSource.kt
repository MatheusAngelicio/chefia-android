package com.example.chefia.data.remote.ai

import android.graphics.Bitmap
import com.example.chefia.data.remote.ai.model.GenerateRecipesResponseDto
import com.example.chefia.data.remote.ai.model.IdentifyIngredientsResponseDto

interface RecipeAiDataSource {

    suspend fun generateRecipes(
        ingredients: List<String>,
        servings: Int,
    ): GenerateRecipesResponseDto

    suspend fun identifyIngredients(
        bitmap: Bitmap,
    ): IdentifyIngredientsResponseDto
}