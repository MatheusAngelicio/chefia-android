package com.example.chefia.core.navigation

import android.net.Uri
import android.os.Bundle
import androidx.navigation.NavType
import com.example.chefia.domain.model.Recipe
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

val RecipeNavType = object : NavType<Recipe>(isNullableAllowed = false) {
    override fun get(bundle: Bundle, key: String): Recipe? {
        return bundle.getString(key)?.let { Json.decodeFromString(it) }
    }

    override fun parseValue(value: String): Recipe {
        return Json.decodeFromString(Uri.decode(value))
    }

    override fun serializeAsValue(value: Recipe): String {
        return Uri.encode(Json.encodeToString(value))
    }

    override fun put(bundle: Bundle, key: String, value: Recipe) {
        bundle.putString(key, Json.encodeToString(value))
    }
}
