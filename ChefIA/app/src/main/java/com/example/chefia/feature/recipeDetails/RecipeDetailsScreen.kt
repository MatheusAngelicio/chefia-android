package com.example.chefia.feature.recipeDetails

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.chefia.core.designsystem.components.ChefIATopBar
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

@Composable
fun RecipeDetailsScreen(
    recipe: Recipe,
    onBackClick: () -> Unit,
) {
    Scaffold(
        topBar = {
            ChefIATopBar(
                onBackClick = onBackClick,
                actions = {
                    IconButton(
                        onClick = {
                            // TODO: Implement revenue sharing via WhatsApp or email.
                        },
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Share,
                            contentDescription = "Compartilhar receita",
                        )
                    }
                },
            )
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
                    RecipeIngredient("Molho Teriyaki", "100ml", true),
                    RecipeIngredient("Brócolis Americano", "1 un", false),
                    RecipeIngredient("Gergelim Branco", "10g", false),
                    RecipeIngredient("Arroz Jasmine", "1 xícara", true),
                ),
                preparationSteps = listOf(
                    RecipeStep(
                        "PREPARAÇÃO",
                        "Lave bem o brócolis e corte em floretes pequenos. Tempere o salmão levemente com sal e pimenta-do-reino (o molho já é salgado)."
                    ),
                    RecipeStep(
                        "COZIMENTO DO ARROZ",
                        "Cozinhe o arroz jasmine com 2 xícaras de água e uma pitada de sal até secar e ficar macio. Reserve quente."
                    ),
                    RecipeStep(
                        "GRELHAR O SALMÃO",
                        "Em uma frigideira antiaderente bem quente, grelhe o salmão por 3 minutos de cada lado. Adicione o molho teriyaki nos minutos finais para caramelizar."
                    ),
                    RecipeStep(
                        "MONTAGEM",
                        "Sirva o salmão sobre o arroz, acompanhado do brócolis no vapor. Finalize com sementes de gergelim e mais molho."
                    )
                )
            )
        )
    }

}