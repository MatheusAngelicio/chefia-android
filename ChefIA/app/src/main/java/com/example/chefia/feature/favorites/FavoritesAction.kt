package com.example.chefia.feature.favorites

import com.example.chefia.domain.model.Recipe

sealed interface FavoritesAction {
    data object BackClicked : FavoritesAction
    data object ExploreRecipesClicked : FavoritesAction
    data class RecipeClicked(val recipe: Recipe) : FavoritesAction
    data class SearchQueryChanged(val query: String) : FavoritesAction
    data class ToggleFavorite(val recipe: Recipe) : FavoritesAction
}
