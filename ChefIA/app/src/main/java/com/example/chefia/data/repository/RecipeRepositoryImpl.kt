package com.example.chefia.data.repository

import com.example.chefia.data.mapper.toDomain
import com.example.chefia.data.remote.ai.RecipeAiDataSource
import com.example.chefia.domain.model.Recipe
import com.example.chefia.domain.repository.RecipeRepository

class RecipeRepositoryImpl(
    private val aiDataSource: RecipeAiDataSource,
) : RecipeRepository {

    override suspend fun generateRecipes(
        ingredients: List<String>,
    ): List<Recipe> {
        return aiDataSource
            .generateRecipes(ingredients)
            .toDomain()
    }
}