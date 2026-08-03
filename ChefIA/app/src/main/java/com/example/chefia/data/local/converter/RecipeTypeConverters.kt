package com.example.chefia.data.local.converter

import androidx.room.TypeConverter
import com.example.chefia.domain.model.RecipeIngredient
import com.example.chefia.domain.model.RecipeStep
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class RecipeTypeConverters {

    @TypeConverter
    fun fromIngredientsList(value: List<RecipeIngredient>): String {
        return Json.encodeToString(value)
    }

    @TypeConverter
    fun toIngredientsList(value: String): List<RecipeIngredient> {
        return Json.decodeFromString(value)
    }

    @TypeConverter
    fun fromStepsList(value: List<RecipeStep>): String {
        return Json.encodeToString(value)
    }

    @TypeConverter
    fun toStepsList(value: String): List<RecipeStep> {
        return try {
            Json.decodeFromString<List<RecipeStep>>(value)
        } catch (e: Exception) {
            // Fallback para o formato antigo (List<String>)
            try {
                Json.decodeFromString<List<String>>(value).map {
                    RecipeStep(title = "PASSO", description = it)
                }
            } catch (e2: Exception) {
                emptyList()
            }
        }
    }
}
