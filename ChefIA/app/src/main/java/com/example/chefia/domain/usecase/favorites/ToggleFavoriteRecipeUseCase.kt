package com.example.chefia.domain.usecase.favorites

import com.example.chefia.domain.model.Recipe
import com.example.chefia.domain.repository.RecipeRepository

class ToggleFavoriteRecipeUseCase(
    private val repository: RecipeRepository,
) {
    suspend operator fun invoke(recipe: Recipe) {
        repository.toggleFavorite(recipe)
    }
}