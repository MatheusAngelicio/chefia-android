package com.example.chefia.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.chefia.data.local.entity.RecipeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(recipe: RecipeEntity)

    @Query("DELETE FROM recipes WHERE id = :id")
    suspend fun deleteFavoriteById(id: String)

    @Query("SELECT id FROM recipes")
    fun getFavoriteIds(): Flow<List<String>>

    @Query("SELECT * FROM recipes")
    fun getFavoriteRecipes(): Flow<List<RecipeEntity>>

    @Query("SELECT EXISTS(SELECT 1 FROM recipes WHERE id = :id)")
    suspend fun isFavorite(id: String): Boolean
}