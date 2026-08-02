package com.example.chefia.domain.usecase.favorites

import com.example.chefia.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.Flow

class ObserveFavoriteRecipeIdsUseCase(
    private val repository: RecipeRepository,
) {
    operator fun invoke(): Flow<Set<String>> {
        return repository.getFavoriteRecipeIds()
    }
}