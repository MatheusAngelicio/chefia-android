package com.example.chefia.feature.ingredients

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.chefia.core.designsystem.components.ChefIAButton
import com.example.chefia.core.designsystem.theme.ChefIATheme
import com.example.chefia.core.designsystem.theme.spacing
import com.example.chefia.feature.ingredients.components.IngredientInput
import com.example.chefia.feature.ingredients.components.IngredientSuggestionChip
import com.example.chefia.feature.ingredients.components.IngredientsCart
import com.example.chefia.feature.ingredients.components.IngredientsCountBadge
import com.example.chefia.feature.ingredients.components.IngredientsTopBar
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun IngredientsScreen(
    onBack: () -> Unit,
    onNavigateToRecipeLoading: () -> Unit,
    viewModel: IngredientsViewModel = koinViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            IngredientsTopBar(
                onBack = onBack,
            )
        },
    ) { innerPadding ->
        IngredientsContent(
            state = state,
            onAction = { action ->
                when (action) {
                    IngredientsAction.FindRecipesClicked -> {
                        viewModel.onAction(action)
                        onNavigateToRecipeLoading()
                    }

                    else -> {
                        viewModel.onAction(action)
                    }
                }
            },
            modifier = Modifier.padding(innerPadding),
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun IngredientsContent(
    state: IngredientsUiState,
    onAction: (IngredientsAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    val spacing = MaterialTheme.spacing

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(
                horizontal = spacing.lg,
                vertical = spacing.md,
            ),
    ) {
        Text(
            text = "Adicione seus\ningredientes",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary,
        )

        Text(
            text = "O ChefIA irá sugerir receitas baseadas no que você já tem em casa.",
            modifier = Modifier.padding(top = spacing.xs),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )

        IngredientInput(
            value = state.currentIngredient,
            onValueChanged = {
                onAction(
                    IngredientsAction.IngredientChanged(it),
                )
            },
            onAddClicked = {
                onAction(IngredientsAction.AddIngredient)
            },
            modifier = Modifier.padding(top = spacing.lg),
        )

        Text(
            text = "SUGESTÕES COMUNS",
            modifier = Modifier.padding(
                top = spacing.md,
                bottom = spacing.xs,
            ),
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(spacing.xs),
            verticalArrangement = Arrangement.spacedBy(spacing.xs),
        ) {
            state.suggestions.forEach { suggestion ->
                IngredientSuggestionChip(
                    name = suggestion.name,
                    emoji = suggestion.emoji,
                    onClick = {
                        onAction(
                            IngredientsAction.SuggestionClicked(
                                ingredient = suggestion.name,
                            ),
                        )
                    },
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = spacing.lg),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "Seu Carrinho",
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground,
            )

            IngredientsCountBadge(
                count = state.ingredients.size,
            )
        }

        IngredientsCart(
            ingredients = state.ingredients,
            onRemoveIngredient = {
                onAction(
                    IngredientsAction.RemoveIngredient(it),
                )
            },
            modifier = Modifier
                .weight(1f)
                .padding(vertical = spacing.md),
        )

        ChefIAButton(
            text = "Encontrar receitas",
            onClick = {
                onAction(
                    IngredientsAction.FindRecipesClicked,
                )
            },
            enabled = state.canFindRecipes,
        )
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
)
@Composable
private fun IngredientsContentEmptyPreview() {
    ChefIATheme {
        IngredientsContent(
            state = IngredientsUiState(),
            onAction = {},
        )
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
)
@Composable
private fun IngredientsContentFilledPreview() {
    ChefIATheme {
        IngredientsContent(
            state = IngredientsUiState(
                currentIngredient = "Tomate",
                ingredients = listOf(
                    "Frango",
                    "Batata",
                    "Cebola",
                ),
            ),
            onAction = {},
        )
    }
}