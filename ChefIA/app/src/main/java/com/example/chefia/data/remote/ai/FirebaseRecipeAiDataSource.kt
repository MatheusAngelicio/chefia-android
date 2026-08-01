package com.example.chefia.data.remote.ai

import com.example.chefia.data.remote.ai.model.GenerateRecipesResponseDto
import com.example.chefia.data.remote.ai.schema.RecipeResponseSchema
import com.google.firebase.ai.GenerativeModel

class FirebaseRecipeAiDataSource(
    private val generativeModel: GenerativeModel,
) : RecipeAiDataSource {

    override suspend fun generateRecipes(
        ingredients: List<String>,
    ): GenerateRecipesResponseDto {
        val prompt = buildPrompt(
            ingredients = ingredients,
        )

        val response = generativeModel.generateObject(
            jsonSchema = RecipeResponseSchema.value,
            prompt = prompt,
        )

        return requireNotNull(response.getObject()) {
            "A IA não retornou nenhuma receita."
        }
    }

    private fun buildPrompt(
        ingredients: List<String>,
    ): String {
        val formattedIngredients = ingredients.joinToString(
            separator = ", ",
        )

        return """
            Ingredientes disponíveis:
            $formattedIngredients

            Gere exatamente 3 receitas diferentes e viáveis.

            Regras:
            - Responda sempre em português do Brasil.
            - Priorize os ingredientes informados pelo usuário.
            - Você pode incluir ingredientes básicos adicionais, como sal, água, óleo e temperos.
            - Marque isAvailable como true somente quando o ingrediente estiver na lista informada.
            - Use instruções simples e seguras.
            - Não invente informações nutricionais.
            - Não inclua explicações fora da estrutura solicitada.
        """.trimIndent()
    }
}