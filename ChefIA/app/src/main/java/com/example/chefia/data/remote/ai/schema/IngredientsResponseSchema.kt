package com.example.chefia.data.remote.ai.schema

import com.example.chefia.data.remote.ai.model.IdentifyIngredientsResponseDto
import com.google.firebase.ai.type.JsonSchema

object IngredientsResponseSchema {

    val value = JsonSchema.obj(
        properties = mapOf(
            "ingredients" to JsonSchema.array(
                items = JsonSchema.string(
                    description = "Nome do ingrediente identificado em português."
                ),
                description = "Lista de ingredientes identificados na imagem.",
                minItems = 1
            )
        ),
        clazz = IdentifyIngredientsResponseDto::class,
        description = "Resultado da identificação de ingredientes em uma imagem."
    )
}