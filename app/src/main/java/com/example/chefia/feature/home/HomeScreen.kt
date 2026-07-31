package com.example.chefia.feature.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CameraAlt
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.chefia.core.designsystem.theme.ChefIATheme
import com.example.chefia.core.designsystem.theme.spacing
import com.example.chefia.feature.home.components.HomeActionCard
import com.example.chefia.feature.home.components.HomeActionCardOrientation

@Composable
fun HomeScreen(
    onNavigateToIngredients: () -> Unit,
) {
    HomeContent(
        onAction = { action ->
            when (action) {
                HomeAction.CameraClicked -> Unit
                HomeAction.TypeIngredientsClicked -> {
                    onNavigateToIngredients()
                }
            }
        },
    )
}

@Composable
private fun HomeContent(
    onAction: (HomeAction) -> Unit,
) {
    val spacing = MaterialTheme.spacing

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                horizontal = spacing.lg,
                vertical = spacing.xl,
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "O que vamos cozinhar hoje?",
            style = MaterialTheme.typography.headlineLarge,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onBackground,
        )

        Text(
            text = "Informe os ingredientes que você tem e a ChefIA encontrará as melhores receitas.",
            modifier = Modifier.padding(
                top = spacing.md,
                bottom = spacing.xxl,
            ),
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(spacing.md),
        ) {
            HomeActionCard(
                title = "Tirar foto da geladeira",
                icon = Icons.Rounded.CameraAlt,
                backgroundColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                iconBackgroundColor =
                    MaterialTheme.colorScheme.primaryContainer,
                onClick = {
                    onAction(HomeAction.CameraClicked)
                },
            )

            HomeActionCard(
                title = "Digitar ingredientes",
                icon = Icons.Rounded.Edit,
                backgroundColor = MaterialTheme.colorScheme.secondary,
                contentColor = MaterialTheme.colorScheme.onSecondary,
                iconBackgroundColor =
                    MaterialTheme.colorScheme.secondaryContainer,
                orientation = HomeActionCardOrientation.Horizontal,
                onClick = {
                    onAction(HomeAction.TypeIngredientsClicked)
                },
            )
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
)
@Composable
private fun HomeContentPreview() {
    ChefIATheme {
        HomeContent(
            onAction = {},
        )
    }
}