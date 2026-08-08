package com.example.chefia.feature.recipeExecution

import com.example.chefia.domain.model.Recipe
import com.example.chefia.domain.model.RecipeStep

data class RecipeExecutionUiState(
    val recipe: Recipe? = null,
    val currentStepIndex: Int = 0,
) {
    val currentStep: RecipeStep?
        get() = recipe?.preparationSteps?.getOrNull(currentStepIndex)

    val totalSteps: Int
        get() = recipe?.preparationSteps?.size ?: 0

    val isFirstStep: Boolean
        get() = currentStepIndex == 0

    val isLastStep: Boolean
        get() = currentStepIndex == (totalSteps - 1)

    val progress: Float
        get() = if (totalSteps > 0) {
            (currentStepIndex + 1).toFloat() / totalSteps
        } else {
            0f
        }
}
