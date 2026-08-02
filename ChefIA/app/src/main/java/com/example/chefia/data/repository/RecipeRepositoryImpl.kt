package com.example.chefia.data.repository

import com.example.chefia.data.mapper.toDomain
import com.example.chefia.data.remote.ai.RecipeAiDataSource
import com.example.chefia.data.local.RecipeLocalDataSource
import com.example.chefia.domain.model.Recipe
import com.example.chefia.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.Flow

class RecipeRepositoryImpl(
    private val aiDataSource: RecipeAiDataSource,
    private val localDataSource: RecipeLocalDataSource,
) : RecipeRepository {

    override suspend fun generateRecipes(
        ingredients: List<String>,
    ): List<Recipe> {
        return aiDataSource
            .generateRecipes(ingredients)
            .toDomain()
    }

    override fun getFavoriteRecipeIds(): Flow<Set<String>> {
        return localDataSource.getFavoriteRecipeIds()
    }

    override suspend fun toggleFavorite(recipe: Recipe) {
        localDataSource.toggleFavorite(recipe)
    }
}