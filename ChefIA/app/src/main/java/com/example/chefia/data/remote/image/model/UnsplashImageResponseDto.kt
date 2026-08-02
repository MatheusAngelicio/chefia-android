package com.example.chefia.data.remote.image.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UnsplashImageResponseDto(
    @SerialName("results") val results: List<UnsplashImageDto> = emptyList()
)

@Serializable
data class UnsplashImageDto(
    @SerialName("urls") val urls: UnsplashUrlsDto
)

@Serializable
data class UnsplashUrlsDto(
    @SerialName("regular") val regular: String
)