package com.example.chefia.data.remote.ai.schema

import com.example.chefia.data.remote.ai.model.GenerateRecipesResponseDto
import com.example.chefia.data.remote.ai.model.RecipeDto
import com.example.chefia.data.remote.ai.model.RecipeIngredientDto
import com.example.chefia.data.remote.ai.model.RecipeStepDto
import com.google.firebase.ai.type.JsonSchema

object RecipeResponseSchema {

    private val stepSchema =
        JsonSchema.obj(
            properties = mapOf(
                "title" to JsonSchema.string(
                    description = "Título curto da etapa em português (ex: PREPARAÇÃO, MONTAGEM).",
                ),
                "description" to JsonSchema.string(
                    description = "Descrição detalhada da etapa em português.",
                ),
            ),
            clazz = RecipeStepDto::class,
            description = "Uma etapa do modo de preparo.",
        )

    private val ingredientSchema =
        JsonSchema.obj(
            properties = mapOf(
                "name" to JsonSchema.string(
                    description = "Nome do ingrediente em português.",
                ),
                "quantity" to JsonSchema.string(
                    description = "Quantidade necessária, como 200 g ou 2 unidades.",
                ),
                "isAvailable" to JsonSchema.boolean(
                    description = "Indica se o ingrediente está na lista informada pelo usuário.",
                ),
            ),
            clazz = RecipeIngredientDto::class,
            description = "Ingrediente necessário para preparar a receita.",
        )

    private val recipeSchema =
        JsonSchema.obj(
            properties = mapOf(
                "name" to JsonSchema.string(
                    description = "Nome curto e atrativo da receita.",
                ),
                "description" to JsonSchema.string(
                    description = "Breve descrição da receita em português.",
                ),
                "preparationTimeMinutes" to JsonSchema.integer(
                    description = "Tempo total estimado em minutos.",
                    minimum = 1.0,
                ),
                "servings" to JsonSchema.integer(
                    description = "Quantidade de porções.",
                    minimum = 1.0,
                ),
                "difficulty" to JsonSchema.enumeration(
                    values = listOf(
                        "Fácil",
                        "Média",
                        "Difícil",
                    ),
                    description = "Nível de dificuldade da receita.",
                ),
                "ingredients" to JsonSchema.array(
                    items = ingredientSchema,
                    description = "Ingredientes necessários para a receita.",
                    minItems = 1,
                ),
                "preparationSteps" to JsonSchema.array(
                    items = stepSchema,
                    description = "Modo de preparo ordenado com títulos e descrições.",
                    minItems = 1,
                ),
                "caloriesPerServingKcal" to JsonSchema.integer(
                    description = "Estimativa de calorias em uma única porção da receita.",
                    minimum = 1.0,
                ),
            ),
            clazz = RecipeDto::class,
            description = "Receita completa criada com os ingredientes disponíveis.",
        )

    val value =
        JsonSchema.obj(
            properties = mapOf(
                "recipes" to JsonSchema.array(
                    items = recipeSchema,
                    description = "Lista com exatamente três receitas diferentes.",
                    minItems = 3,
                    maxItems = 3,
                ),
            ),
            clazz = GenerateRecipesResponseDto::class,
            description = "Resultado da geração de receitas.",
        )
}