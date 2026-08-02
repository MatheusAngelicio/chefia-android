package com.example.chefia.data.remote.image

interface RecipeImageDataSource {
    suspend fun searchImageUrl(query: String): String?
}
