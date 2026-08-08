package com.example.chefia.data.repository

import android.graphics.Bitmap
import com.example.chefia.data.mapper.toDomain
import com.example.chefia.data.remote.ai.RecipeAiDataSource
import com.example.chefia.data.local.RecipeLocalDataSource
import com.example.chefia.data.remote.image.RecipeImageDataSource
import com.example.chefia.domain.model.Recipe
import com.example.chefia.domain.repository.RecipeRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.Flow

class RecipeRepositoryImpl(
    private val aiDataSource: RecipeAiDataSource,
    private val localDataSource: RecipeLocalDataSource,
    private val imageDataSource: RecipeImageDataSource,
) : RecipeRepository {

    override fun generateRecipes(
        ingredients: List<String>,
        servings: Int,
    ): Flow<List<Recipe>> = flow {
        val recipes = aiDataSource
            .generateRecipes(
                ingredients = ingredients,
                servings = servings,
            )
            .toDomain()

        // Emite as receitas sem imagens primeiro para exibir na UI rapidamente.
        emit(recipes)

        // Busca as imagens em paralelo.
        val recipesWithImages = coroutineScope {
            recipes.map { recipe ->
                async {
                    val imageUrl = imageDataSource.searchImageUrl(
                        query = recipe.name,
                    )

                    recipe.copy(
                        imageUrl = imageUrl,
                    )
                }
            }.awaitAll()
        }

        // Emite novamente com as URLs das imagens preenchidas.
        emit(recipesWithImages)
    }

    override fun identifyIngredientsFromImage(bitmap: Bitmap): Flow<List<String>> = flow {
        val response = aiDataSource.identifyIngredients(bitmap)
        emit(response.ingredients)
    }

    override fun getFavoriteRecipeIds(): Flow<Set<String>> {
        return localDataSource.getFavoriteRecipeIds()
    }

    override fun getFavoriteRecipes(): Flow<List<Recipe>> {
        return localDataSource.getFavoriteRecipes()
    }

    override suspend fun toggleFavorite(recipe: Recipe) {
        localDataSource.toggleFavorite(recipe)
    }
}