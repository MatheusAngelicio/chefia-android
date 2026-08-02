package com.example.chefia.data.remote.ai

import android.util.Log
import com.example.chefia.data.remote.ai.model.GenerateRecipesResponseDto
import com.example.chefia.data.remote.ai.schema.RecipeResponseSchema
import com.google.firebase.ai.GenerativeModel

private const val TAG = "ChefIA_AI"

class FirebaseRecipeAiDataSource(
    private val generativeModel: GenerativeModel,
) : RecipeAiDataSource {

    override suspend fun generateRecipes(
        ingredients: List<String>,
        isFitness: Boolean,
        isBudget: Boolean,
    ): GenerateRecipesResponseDto {
        val prompt = buildPrompt(
            ingredients = ingredients,
            isFitness = isFitness,
            isBudget = isBudget,
        )

        Log.d(TAG, "Enviando prompt para o Firebase AI:\n$prompt")

        return try {
            val response = generativeModel.generateObject(
                jsonSchema = RecipeResponseSchema.value,
                prompt = prompt,
            )

            val result = requireNotNull(response.getObject()) {
                "A IA não retornou nenhuma receita."
            }

            Log.d(TAG, "Resposta da IA recebida com sucesso: $result")

            result
        } catch (e: Exception) {
            Log.e(TAG, "Erro na comunicação com o Firebase AI", e)
            throw e
        }
    }

    private fun buildPrompt(
        ingredients: List<String>,
        isFitness: Boolean,
        isBudget: Boolean,
    ): String {
        val formattedIngredients = ingredients.joinToString(
            separator = ", ",
        )

        val fitnessInstruction = if (isFitness) {
            "- Gere receitas saudáveis, nutritivas e com baixo teor calórico (fitness)."
        } else ""

        val budgetInstruction = if (isBudget) {
            "- Priorize receitas econômicas, utilizando ingredientes simples e acessíveis (baratas)."
        } else ""

        return """
            Ingredientes disponíveis:
            $formattedIngredients

            Gere exatamente 3 receitas diferentes e viáveis.

            Regras:
            - Responda sempre em português do Brasil.
            - Priorize os ingredientes informados pelo usuário.
            $fitnessInstruction
            $budgetInstruction
            - Você pode incluir ingredientes básicos adicionais, como sal, água, óleo e temperos.
            - Marque isAvailable como true somente quando o ingrediente estiver na lista informada.
            - Use instruções simples e seguras.
            - Não invente informações nutricionais.
            - Não inclua explicações fora da estrutura solicitada.
        """.trimIndent()
    }
}