package com.example.chefia.domain.usecase.favorites

import com.example.chefia.domain.model.Recipe
import com.example.chefia.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.Flow

class ObserveFavoriteRecipesUseCase(
    private val repository: RecipeRepository,
) {
    operator fun invoke(): Flow<List<Recipe>> {
        return repository.getFavoriteRecipes()
    }
}