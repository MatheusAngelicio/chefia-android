package com.example.chefia.data.local

import com.example.chefia.data.local.dao.RecipeDao
import com.example.chefia.data.local.entity.RecipeEntity
import com.example.chefia.data.local.entity.toDomain
import com.example.chefia.data.local.entity.toEntity
import com.example.chefia.domain.model.Recipe
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface RecipeLocalDataSource {
    fun getFavoriteRecipeIds(): Flow<Set<String>>
    suspend fun toggleFavorite(recipe: Recipe)
    fun getFavoriteRecipes(): Flow<List<Recipe>>
}

class RoomRecipeLocalDataSource(
    private val recipeDao: RecipeDao,
) : RecipeLocalDataSource {

    override fun getFavoriteRecipeIds(): Flow<Set<String>> {
        return recipeDao.getFavoriteIds().map { it.toSet() }
    }

    override suspend fun toggleFavorite(recipe: Recipe) {
        val isFavorite = recipeDao.isFavorite(recipe.id)
        if (isFavorite) {
            recipeDao.deleteFavoriteById(recipe.id)
        } else {
            recipeDao.insertFavorite(recipe.toEntity())
        }
    }

    override fun getFavoriteRecipes(): Flow<List<Recipe>> {
        return recipeDao.getFavoriteRecipes().map { entities ->
            entities.map { it.toDomain() }
        }
    }
}