package com.example.chefia.data.remote.ai.model

import kotlinx.serialization.Serializable

@Serializable
data class IdentifyIngredientsResponseDto(
    val ingredients: List<String>,
)