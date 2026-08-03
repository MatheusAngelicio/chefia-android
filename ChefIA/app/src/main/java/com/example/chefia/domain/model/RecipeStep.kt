package com.example.chefia.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class RecipeStep(
    val title: String,
    val description: String,
)
