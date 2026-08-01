package com.example.chefia.domain.usecase

import com.example.chefia.domain.model.Recipe
import com.example.chefia.domain.repository.RecipeRepository
import java.util.Locale

class GenerateRecipesUseCase(
    private val repository: RecipeRepository,
) {

    suspend operator fun invoke(
        ingredients: List<String>,
    ): List<Recipe> {
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