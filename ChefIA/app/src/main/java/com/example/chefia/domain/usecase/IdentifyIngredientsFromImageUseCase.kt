package com.example.chefia.domain.usecase

import android.graphics.Bitmap
import com.example.chefia.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.Flow

class IdentifyIngredientsFromImageUseCase(
    private val repository: RecipeRepository,
) {
    operator fun invoke(bitmap: Bitmap): Flow<List<String>> {
        return repository.identifyIngredientsFromImage(bitmap)
    }
}