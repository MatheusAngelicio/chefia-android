package com.example.chefia.feature.recipeDetails

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.chefia.core.designsystem.components.ChefIAButton
import com.example.chefia.core.designsystem.components.ChefIATopBar
import com.example.chefia.core.designsystem.theme.ChefIAColors
import com.example.chefia.core.designsystem.theme.ChefIATheme
import com.example.chefia.core.designsystem.theme.spacing
import com.example.chefia.domain.model.Recipe
import com.example.chefia.domain.model.RecipeDifficulty
import com.example.chefia.domain.model.RecipeIngredient
import com.example.chefia.domain.model.RecipeStep
import com.example.chefia.feature.recipeDetails.components.RecipeDetailsHeader
import com.example.chefia.feature.recipeDetails.components.RecipeDetailsIngredients
import com.example.chefia.feature.recipeDetails.components.RecipeDetailsPreparation
import com.example.chefia.feature.recipeDetails.components.RecipeDetailsSummary
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun RecipeDetailsScreen(
    recipe: Recipe,
    onBackClick: () -> Unit,
    onStartRecipeClick: (Recipe) -> Unit,
    viewModel: RecipeDetailsViewModel = koinViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val spacing = MaterialTheme.spacing

    LaunchedEffect(recipe.id) {
        viewModel.observeFavoriteStatus(recipe.id)
    }

    Scaffold(
        topBar = {
            ChefIATopBar(
                onBackClick = onBackClick,
                actions = {
                    IconButton(
                        onClick = { viewModel.toggleFavorite(recipe) },
                    ) {
                        Icon(
                            imageVector = if (state.isFavorite) {
                                Icons.Rounded.Favorite
                            } else {
                                Icons.Rounded.FavoriteBorder
                            },
                            contentDescription = if (state.isFavorite) {
                                "Remover dos favoritos"
                            } else {
                                "Adicionar aos favoritos"
                            },
                            tint = if (state.isFavorite) {
                                MaterialTheme.colorScheme.primary
                            } else {
                                MaterialTheme.colorScheme.onSurfaceVariant
                            },
                        )
                    }
                },
            )
        },
        bottomBar = {
            Column {
                HorizontalDivider(
                    thickness = 1.dp,
                    color = ChefIAColors.Border
                )
                Surface(
                    color = MaterialTheme.colorScheme.background,
                ) {
                    ChefIAButton(
                        text = "Iniciar Receita",
                        onClick = { onStartRecipeClick(recipe) },
                        modifier = Modifier
                            .padding(spacing.lg)
                            .fillMaxWidth(),
                    )
                }
            }
        },
    ) { innerPadding ->
        RecipeDetailsContent(
            recipe = recipe,
            modifier = Modifier.padding(innerPadding),
        )
    }
}

@Composable
private fun RecipeDetailsContent(
    recipe: Recipe,
    modifier: Modifier = Modifier,
) {
    val spacing = MaterialTheme.spacing

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(
            bottom = spacing.xl,
        ),
    ) {
        item {
            RecipeDetailsHeader(
                recipe = recipe,
            )
        }

        item {
            RecipeDetailsSummary(
                recipe = recipe,
            )
        }

        item {
            HorizontalDivider(
                modifier = Modifier.padding(bottom = spacing.sm),
                thickness = 1.5.dp,
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
            )
        }

        item {
            RecipeDetailsIngredients(
                ingredients = recipe.ingredients,
            )
        }

        item {
            RecipeDetailsPreparation(
                steps = recipe.preparationSteps,
                modifier = Modifier.padding(top = spacing.lg)
            )
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
)
@Composable
private fun RecipeDetailsContentPreview() {
    ChefIATheme {
        RecipeDetailsContent(
            recipe = Recipe(
                id = "1",
                name = "Omelete de Queijo",
                description = "Um omelete rápido e fácil",
                preparationTimeMinutes = 10,
                servings = 1,
                caloriesPerServingKcal = 200,
                difficulty = RecipeDifficulty.EASY,
                ingredients = listOf(
                    RecipeIngredient("2 filés de Salmão Fresco", "400g", true),
                ),
                preparationSteps = listOf(
                    RecipeStep(
                        "PREPARAÇÃO",
                        "Lave bem o brócolis e corte em floretes pequenos. Tempere o salmão levemente com sal e pimenta-do-reino (o molho já é salgado)."
                    ),
                )
            )
        )
    }
}
