package com.example.chefia.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.chefia.data.local.converter.RecipeTypeConverters
import com.example.chefia.data.local.dao.RecipeDao
import com.example.chefia.data.local.entity.RecipeEntity

@Database(
    entities = [RecipeEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(RecipeTypeConverters::class)
abstract class ChefIADatabase : RoomDatabase() {
    abstract val recipeDao: RecipeDao
}