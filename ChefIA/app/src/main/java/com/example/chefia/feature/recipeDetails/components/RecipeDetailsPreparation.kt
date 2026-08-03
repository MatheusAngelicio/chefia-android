package com.example.chefia.feature.recipeDetails.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.chefia.core.designsystem.theme.ChefIATheme
import com.example.chefia.core.designsystem.theme.spacing
import com.example.chefia.domain.model.RecipeStep

@Composable
fun RecipeDetailsPreparation(
    steps: List<RecipeStep>,
    modifier: Modifier = Modifier,
) {
    val spacing = MaterialTheme.spacing

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = spacing.lg),
        verticalArrangement = Arrangement.spacedBy(spacing.md),
    ) {
        Text(
            text = "Modo de Preparo",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = spacing.xs)
        )

        steps.forEachIndexed { index, step ->
            PreparationStepItem(
                stepNumber = index + 1,
                step = step
            )
        }
    }
}

@Composable
private fun PreparationStepItem(
    stepNumber: Int,
    step: RecipeStep,
    modifier: Modifier = Modifier,
) {
    val spacing = MaterialTheme.spacing

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFFF5F5F5))
            .padding(spacing.md),
        horizontalArrangement = Arrangement.spacedBy(spacing.md),
    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stepNumber.toString(),
                color = Color.White,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(spacing.xs)
        ) {
            Text(
                text = step.title.uppercase(),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = step.description,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface,
                lineHeight = MaterialTheme.typography.bodyLarge.lineHeight * 1.2
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun RecipeDetailsPreparationPreview() {
    ChefIATheme {
        RecipeDetailsPreparation(
            steps = listOf(
                RecipeStep(
                    "PREPARAÇÃO",
                    "Lave bem o brócolis e corte em floretes pequenos. Tempere o salmão levemente com sal e pimenta-do-reino (o molho já é salgado)."
                ),
                RecipeStep(
                    "COZIMENTO DO ARROZ",
                    "Cozinhe o arroz jasmine com 2 xícaras de água e uma pitada de sal até secar e ficar macio. Reserve quente."
                )
            )
        )
    }
}
