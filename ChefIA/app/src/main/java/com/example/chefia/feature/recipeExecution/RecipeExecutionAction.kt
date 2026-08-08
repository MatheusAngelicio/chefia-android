package com.example.chefia.feature.recipeExecution

sealed interface RecipeExecutionAction {
    data object NextStep : RecipeExecutionAction
    data object PreviousStep : RecipeExecutionAction
    data object FinishRecipe : RecipeExecutionAction
}
