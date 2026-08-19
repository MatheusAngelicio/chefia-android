package com.example.chefia.feature.recipeExecution

import app.cash.turbine.test
import com.example.chefia.domain.model.Recipe
import com.example.chefia.domain.model.RecipeDifficulty
import com.example.chefia.domain.model.RecipeStep
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class RecipeExecutionViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()
    private lateinit var viewModel: RecipeExecutionViewModel

    private val mockRecipe = Recipe(
        id = "1",
        name = "Test Recipe",
        description = "Description",
        preparationTimeMinutes = 30,
        servings = 2,
        caloriesPerServingKcal = 500,
        difficulty = RecipeDifficulty.EASY,
        ingredients = emptyList(),
        preparationSteps = listOf(
            RecipeStep("Step 1", "Instructions 1"),
            RecipeStep("Step 2", "Instructions 2")
        )
    )

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = RecipeExecutionViewModel()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `init should update state with recipe`() = runTest {
        viewModel.init(mockRecipe)

        viewModel.uiState.test {
            val state = awaitItem()
            assertThat(state.recipe).isEqualTo(mockRecipe)
            assertThat(state.currentStepIndex).isEqualTo(0)
            assertThat(state.totalSteps).isEqualTo(2)
        }
    }

    @Test
    fun `NextStep should increment currentStepIndex`() = runTest {
        viewModel.init(mockRecipe)
        
        viewModel.onAction(RecipeExecutionAction.NextStep)

        viewModel.uiState.test {
            val state = awaitItem()
            assertThat(state.currentStepIndex).isEqualTo(1)
        }
    }
}
