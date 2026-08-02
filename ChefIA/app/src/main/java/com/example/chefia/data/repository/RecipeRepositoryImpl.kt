package com.example.chefia.data.repository

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
        isFitness: Boolean,
        isBudget: Boolean,
    ): Flow<List<Recipe>> = flow {
        val recipes = aiDataSource
            .generateRecipes(
                ingredients = ingredients,
                isFitness = isFitness,
                isBudget = isBudget,
            )
            .toDomain()

        // Emitir receitas sem as imagens primeiro para exibir na UI rapidamente
        emit(recipes)

        // Buscar imagens em paralelo
        val recipesWithImages = coroutineScope {
            recipes.map { recipe ->
                async {
                    val imageUrl = imageDataSource.searchImageUrl(recipe.name)
                    recipe.copy(imageUrl = imageUrl)
                }
            }.awaitAll()
        }

        // Emitir novamente com as URLs das imagens preenchidas
        emit(recipesWithImages)
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