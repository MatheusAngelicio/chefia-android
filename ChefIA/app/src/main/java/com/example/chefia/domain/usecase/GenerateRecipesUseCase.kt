package com.example.chefia.domain.usecase

import com.example.chefia.domain.model.Recipe
import com.example.chefia.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.Flow
import java.util.Locale

class GenerateRecipesUseCase(
    private val repository: RecipeRepository,
) {

    operator fun invoke(
        ingredients: List<String>,
    ): Flow<List<Recipe>> {
        val normalizedIngredients = ingredients
            .map(String::trim)
            .filter(String::isNotBlank)
            .distinctBy {
                it.lowercase(Locale.ROOT)
            }

        require(normalizedIngredients.isNotEmpty()) {
            "Adicione pelo menos um ingrediente."
        }

        return repository.generateRecipes(
            ingredients = normalizedIngredients,
        )
    }
}