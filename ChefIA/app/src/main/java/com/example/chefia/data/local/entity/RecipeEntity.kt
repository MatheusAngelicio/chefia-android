package com.example.chefia.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.chefia.domain.model.Recipe
import com.example.chefia.domain.model.RecipeDifficulty
import com.example.chefia.domain.model.RecipeIngredient
import com.example.chefia.domain.model.RecipeStep

@Entity(tableName = "recipes")
data class RecipeEntity(
    @PrimaryKey val id: String,
    val name: String,
    val description: String,
    val preparationTimeMinutes: Int,
    val servings: Int,
    val caloriesPerServingKcal: Int,
    val difficulty: RecipeDifficulty,
    val ingredients: List<RecipeIngredient>,
    val preparationSteps: List<RecipeStep>,
    val imageUrl: String? = null,
    val favoritedAt: Long = System.currentTimeMillis(),
)

fun RecipeEntity.toDomain() = Recipe(
    id = id,
    name = name,
    description = description,
    preparationTimeMinutes = preparationTimeMinutes,
    servings = servings,
    caloriesPerServingKcal = caloriesPerServingKcal,
    difficulty = difficulty,
    ingredients = ingredients,
    preparationSteps = preparationSteps,
    imageUrl = imageUrl,
)

fun Recipe.toEntity() = RecipeEntity(
    id = id,
    name = name,
    description = description,
    preparationTimeMinutes = preparationTimeMinutes,
    servings = servings,
    difficulty = difficulty,
    ingredients = ingredients,
    caloriesPerServingKcal = caloriesPerServingKcal,
    preparationSteps = preparationSteps,
    imageUrl = imageUrl,
    favoritedAt = System.currentTimeMillis(),
)