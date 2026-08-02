package com.example.chefia.data.remote.image

import android.util.Log
import com.example.chefia.BuildConfig
import com.example.chefia.data.remote.image.model.UnsplashImageResponseDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter

private const val TAG = "UnsplashImageDS"
private const val FALLBACK_IMAGE_URL = "https://plus.unsplash.com/premium_photo-1673108852141-e8c3c22a4a22?q=80&w=2340&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"

class UnsplashImageDataSource(
    private val httpClient: HttpClient,
) : RecipeImageDataSource {

    override suspend fun searchImageUrl(query: String): String {
        val accessKey = BuildConfig.UNSPLASH_ACCESS_KEY
        
        if (accessKey.isBlank()) {
            Log.w(TAG, "Unsplash Access Key não configurada. Usando fallback.")
            return FALLBACK_IMAGE_URL
        }

        return try {
            val response: UnsplashImageResponseDto = httpClient.get("https://api.unsplash.com/search/photos") {
                header("Authorization", "Client-ID $accessKey")
                parameter("query", "food $query")
                parameter("per_page", "1")
                parameter("orientation", "landscape")
            }.body()

            val imageUrl = response.results.firstOrNull()?.urls?.regular

            if (imageUrl != null) {
                Log.d(TAG, "Imagem Unsplash encontrada para '$query': $imageUrl")
                imageUrl
            } else {
                Log.w(TAG, "Nenhuma imagem Unsplash encontrada para '$query', usando fallback.")
                FALLBACK_IMAGE_URL
            }
        } catch (e: Exception) {
            Log.e(TAG, "Erro ao buscar imagem no Unsplash para '$query'", e)
            FALLBACK_IMAGE_URL
        }
    }
}