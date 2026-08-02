package com.example.chefia.di

import androidx.room.Room
import com.example.chefia.data.local.RecipeLocalDataSource
import com.example.chefia.data.local.RoomRecipeLocalDataSource
import com.example.chefia.data.local.database.ChefIADatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            ChefIADatabase::class.java,
            "chefia.db"
        ).fallbackToDestructiveMigration().build()
    }

    single { get<ChefIADatabase>().recipeDao }

    single<RecipeLocalDataSource> {
        RoomRecipeLocalDataSource(
            recipeDao = get(),
        )
    }
}