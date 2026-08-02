package com.example.chefia.domain.repository

import com.example.chefia.domain.model.Recipe
import kotlinx.coroutines.flow.Flow

interface RecipeRepository {

    fun generateRecipes(
        ingredients: List<String>,
    ): Flow<List<Recipe>>

    fun getFavoriteRecipeIds(): Flow<Set<String>>

    fun getFavoriteRecipes(): Flow<List<Recipe>>

    suspend fun toggleFavorite(recipe: Recipe)
}