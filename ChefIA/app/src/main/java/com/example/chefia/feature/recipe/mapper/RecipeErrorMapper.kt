package com.example.chefia.feature.recipe.mapper

import android.accounts.NetworkErrorException
import com.example.chefia.R
import com.example.chefia.core.common.UiText
import com.google.firebase.ai.type.ContentBlockedException
import com.google.firebase.ai.type.FirebaseAIException
import com.google.firebase.ai.type.PromptBlockedException
import com.google.firebase.ai.type.QuotaExceededException
import com.google.firebase.ai.type.RequestTimeoutException
import com.google.firebase.ai.type.ServerException
import java.io.IOException

object RecipeErrorMapper {
    fun map(throwable: Throwable): UiText {
        if (isNetworkError(throwable)) {
            return UiText.ResourceString(R.string.error_no_internet)
        }

        return when (throwable) {
            is NetworkErrorException -> UiText.ResourceString(R.string.error_no_internet)
            is QuotaExceededException -> UiText.ResourceString(R.string.error_quota_exceeded)
            is PromptBlockedException, is ContentBlockedException -> UiText.ResourceString(R.string.error_safety_blocked)
            is RequestTimeoutException -> UiText.ResourceString(R.string.error_timeout)
            is ServerException -> UiText.ResourceString(R.string.error_server)
            is FirebaseAIException -> {
                val message = throwable.message?.lowercase() ?: ""
                mapByMessage(message)
            }

            else -> mapByMessage(throwable.message?.lowercase() ?: "")
        }
    }

    private fun isNetworkError(throwable: Throwable): Boolean {
        var cause: Throwable? = throwable
        while (cause != null) {
            if (cause is IOException) {
                return true
            }
            cause = cause.cause
        }
        return false
    }

    private fun mapByMessage(message: String): UiText {
        return when {
            message.contains("quota") -> UiText.ResourceString(R.string.error_quota_exceeded)
            message.contains("safety") -> UiText.ResourceString(R.string.error_safety_blocked)
            message.contains("timeout") -> UiText.ResourceString(R.string.error_timeout)
            message.contains("server") || message.contains("500") -> UiText.ResourceString(R.string.error_server)
            else -> UiText.ResourceString(R.string.error_generic)
        }
    }
}