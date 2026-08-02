package com.example.chefia.data.local.converter

import androidx.room.TypeConverter
import com.example.chefia.domain.model.RecipeIngredient
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
    fun fromStepsList(value: List<String>): String {
        return Json.encodeToString(value)
    }

    @TypeConverter
    fun toStepsList(value: String): List<String> {
        return Json.decodeFromString(value)
    }
}