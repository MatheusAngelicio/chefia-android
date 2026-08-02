package com.example.chefia.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.chefia.domain.model.Recipe
import com.example.chefia.domain.model.RecipeDifficulty
import com.example.chefia.domain.model.RecipeIngredient

@Entity(tableName = "recipes")
data class RecipeEntity(
    @PrimaryKey val id: String,
    val name: String,
    val description: String,
    val preparationTimeMinutes: Int,
    val servings: Int,
    val difficulty: RecipeDifficulty,
    val ingredients: List<RecipeIngredient>,
    val preparationSteps: List<String>,
)

fun RecipeEntity.toDomain() = Recipe(
    id = id,
    name = name,
    description = description,
    preparationTimeMinutes = preparationTimeMinutes,
    servings = servings,
    difficulty = difficulty,
    ingredients = ingredients,
    preparationSteps = preparationSteps,
)

fun Recipe.toEntity() = RecipeEntity(
    id = id,
    name = name,
    description = description,
    preparationTimeMinutes = preparationTimeMinutes,
    servings = servings,
    difficulty = difficulty,
    ingredients = ingredients,
    preparationSteps = preparationSteps,
)