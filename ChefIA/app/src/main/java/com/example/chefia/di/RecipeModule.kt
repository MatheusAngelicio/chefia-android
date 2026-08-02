package com.example.chefia.di

import com.example.chefia.data.remote.ai.FirebaseRecipeAiDataSource
import com.example.chefia.data.remote.ai.RecipeAiDataSource
import com.example.chefia.data.remote.image.RecipeImageDataSource
import com.example.chefia.data.remote.image.UnsplashImageDataSource
import com.example.chefia.data.repository.RecipeRepositoryImpl
import com.example.chefia.domain.repository.RecipeRepository
import com.example.chefia.domain.usecase.GenerateRecipesUseCase
import com.example.chefia.domain.usecase.favorites.ObserveFavoriteRecipeIdsUseCase
import com.example.chefia.domain.usecase.favorites.ObserveFavoriteRecipesUseCase
import com.example.chefia.domain.usecase.favorites.ToggleFavoriteRecipeUseCase
import com.example.chefia.feature.recipe.RecipeGenerationViewModel
import com.google.firebase.Firebase
import com.google.firebase.ai.GenerativeModel
import com.google.firebase.ai.ai
import com.google.firebase.ai.type.GenerativeBackend
import com.google.firebase.ai.type.content
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

private const val CHEFIA_MODEL_NAME = "gemini-3.5-flash"

val recipeModule = module {

    single<GenerativeModel> {
        Firebase
            .ai(
                backend = GenerativeBackend.googleAI(),
            )
            .generativeModel(
                modelName = CHEFIA_MODEL_NAME,
                systemInstruction = content {
                    text(
                        """
                            Você é o ChefIA, um assistente especializado em culinária.
                            Crie receitas práticas, coerentes e seguras.
                            Responda sempre em português do Brasil.
                            Respeite rigorosamente o schema de resposta solicitado.
                        """.trimIndent(),
                    )
                },
            )
    }

    single<RecipeAiDataSource> {
        FirebaseRecipeAiDataSource(
            generativeModel = get(),
        )
    }

    single<RecipeImageDataSource> {
        UnsplashImageDataSource(
            httpClient = get(),
        )
    }

    single<RecipeRepository> {
        RecipeRepositoryImpl(
            aiDataSource = get(),
            localDataSource = get(),
            imageDataSource = get(),
        )
    }

    factory {
        GenerateRecipesUseCase(
            repository = get(),
        )
    }

    factory {
        ObserveFavoriteRecipeIdsUseCase(
            repository = get(),
        )
    }

    factory {
        ObserveFavoriteRecipesUseCase(
            repository = get(),
        )
    }

    factory {
        ToggleFavoriteRecipeUseCase(
            repository = get(),
        )
    }

    viewModelOf(::RecipeGenerationViewModel)
}