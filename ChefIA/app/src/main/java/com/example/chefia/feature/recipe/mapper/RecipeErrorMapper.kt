package com.example.chefia.feature.recipe.mapper

import com.example.chefia.R
import com.example.chefia.core.common.UiText
import java.io.IOException

object RecipeErrorMapper {
    fun map(throwable: Throwable): UiText {
        return when (throwable) {
            is IOException -> UiText.ResourceString(R.string.error_no_internet)
            else -> {
                val message = throwable.message?.lowercase() ?: ""
                when {
                    message.contains("quota") -> UiText.ResourceString(R.string.error_quota_exceeded)
                    message.contains("safety") -> UiText.ResourceString(R.string.error_safety_blocked)
                    message.contains("timeout") -> UiText.ResourceString(R.string.error_timeout)
                    message.contains("server") || message.contains("500") -> UiText.ResourceString(R.string.error_server)
                    else -> UiText.ResourceString(R.string.error_generic)
                }
            }
        }
    }
}