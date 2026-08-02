package com.example.chefia.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class RecipeIngredient(
    val name: String,
    val quantity: String,
    val isAvailable: Boolean,
)