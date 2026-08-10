package com.example.chefia.data.remote.ai

import android.graphics.Bitmap
import android.util.Log
import com.example.chefia.data.remote.ai.model.GenerateRecipesResponseDto
import com.example.chefia.data.remote.ai.model.IdentifyIngredientsResponseDto
import com.example.chefia.data.remote.ai.schema.IngredientsResponseSchema
import com.example.chefia.data.remote.ai.schema.RecipeResponseSchema
import com.google.firebase.ai.GenerativeModel
import com.google.firebase.ai.type.content

private const val TAG = "ChefIA_AI"

class FirebaseRecipeAiDataSource(
    private val generativeModel: GenerativeModel,
) : RecipeAiDataSource {

    override suspend fun generateRecipes(
        ingredients: List<String>,
        servings: Int,
    ): GenerateRecipesResponseDto {
        val prompt = buildPrompt(
            ingredients = ingredients,
            servings = servings,
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

            Log.d(
                TAG,
                "Resposta da IA recebida com sucesso: $result",
            )

            result
        } catch (exception: Exception) {
            Log.e(
                TAG,
                "Erro na comunicação com o Firebase AI",
                exception,
            )

            throw exception
        }
    }

    override suspend fun identifyIngredients(bitmap: Bitmap): IdentifyIngredientsResponseDto {
        val prompt = content {
            image(bitmap)
            text(
                """
                Analise esta imagem e identifique todos os ingredientes culinários visíveis.
                
                Regras:
                - Retorne apenas ingredientes que podem ser usados em receitas.
                - Use nomes comuns em português do Brasil.
                - Ignore objetos que não são comida.
                - Se não houver ingredientes culinários visíveis, retorne a lista de ingredientes vazia [].
                - Não retorne mensagens de erro ou avisos dentro da lista de ingredientes.
                """.trimIndent(),
            )
        }

        Log.d(TAG, "Enviando imagem para identificação de ingredientes")

        return try {
            val response = generativeModel.generateObject(
                jsonSchema = IngredientsResponseSchema.value,
                prompt = prompt,
            )

            val result = requireNotNull(response.getObject()) {
                "A IA não conseguiu identificar os ingredientes."
            }

            Log.d(TAG, "Ingredientes identificados: ${result.ingredients}")

            result
        } catch (exception: Exception) {
            Log.e(TAG, "Erro ao identificar ingredientes", exception)
            throw exception
        }
    }

    private fun buildPrompt(
        ingredients: List<String>,
        servings: Int,
    ): String {
        val formattedIngredients = ingredients.joinToString(
            separator = ", ",
        )

        return """
            Ingredientes disponíveis:
            $formattedIngredients

            Quantidade de porções:
            $servings

            Gere exatamente 3 receitas diferentes e viáveis.

            Regras:
            - Responda sempre em português do Brasil.
            - Todas as receitas devem render exatamente $servings ${if (servings == 1) "porção" else "porções"}.
            - Ajuste as quantidades dos ingredientes para a quantidade de porções solicitada.
            - Seja extremamente conciso no campo de quantidade (ex: "500g", "1 un", "1/2 xícara"). Evite textos longos ou explicações adicionais na quantidade.
            - Priorize os ingredientes informados pelo usuário.
            - Você pode incluir ingredientes básicos adicionais, como sal, água, óleo e temperos.
            - Marque isAvailable como true somente quando o ingrediente estiver na lista informada pelo usuário.
            - Informe uma estimativa coerente de calorias por porção no campo caloriesPerServingKcal.
            - O campo caloriesPerServingKcal deve conter somente um número inteiro.
            - Considere o tamanho das porções e as quantidades informadas ao estimar as calorias.
            - Use instruções simples, claras e seguras.
            - Não apresente as calorias como uma informação nutricional exata.
            - Não inclua explicações fora da estrutura solicitada.
        """.trimIndent()
    }

}