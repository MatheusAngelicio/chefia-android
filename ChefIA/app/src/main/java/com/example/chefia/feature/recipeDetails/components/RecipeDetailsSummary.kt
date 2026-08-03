package com.example.chefia.feature.recipeDetails.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccessTime
import androidx.compose.material.icons.rounded.BarChart
import androidx.compose.material.icons.rounded.Groups
import androidx.compose.material.icons.rounded.LocalFireDepartment
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.chefia.core.designsystem.theme.ChefIATheme
import com.example.chefia.core.designsystem.theme.spacing
import com.example.chefia.domain.model.Recipe
import com.example.chefia.domain.model.RecipeDifficulty

@Composable
fun RecipeDetailsSummary(
    recipe: Recipe,
    modifier: Modifier = Modifier,
) {
    val spacing = MaterialTheme.spacing

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = spacing.lg),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        SummaryItem(
            icon = Icons.Rounded.AccessTime,
            label = "TEMPO",
            value = "${recipe.preparationTimeMinutes} min",
        )
        SummaryItem(
            icon = Icons.Rounded.Groups,
            label = "PORÇÕES",
            value = if (recipe.servings == 1) "1 pessoa" else "${recipe.servings} pessoas",
        )
        SummaryItem(
            icon = Icons.Rounded.BarChart,
            label = "NÍVEL",
            value = recipe.difficultyToDisplayName,
        )
        SummaryItem(
            icon = Icons.Rounded.LocalFireDepartment,
            label = "KCAL",
            value = "${recipe.caloriesPerServingKcal} kcal",
        )
    }
}

@Composable
private fun SummaryItem(
    icon: ImageVector,
    label: String,
    value: String,
    modifier: Modifier = Modifier,
) {
    val spacing = MaterialTheme.spacing

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(spacing.xs),
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(24.dp),
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun RecipeDetailsSummaryPreview() {
    ChefIATheme {
        RecipeDetailsSummary(
            recipe = Recipe(
                id = "1",
                name = "Salmão com Teriyaki",
                description = "",
                preparationTimeMinutes = 25,
                servings = 2,
                caloriesPerServingKcal = 450,
                difficulty = RecipeDifficulty.EASY,
                ingredients = emptyList(),
                preparationSteps = emptyList()
            )
        )
    }
}
