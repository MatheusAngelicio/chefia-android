package com.example.chefia.feature.ingredients.components

import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.chefia.core.designsystem.theme.ChefIATheme

@Composable
fun IngredientSuggestionChip(
    name: String,
    emoji: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    AssistChip(
        onClick = onClick,
        modifier = modifier,
        label = {
            Text(
                text = "$emoji $name",
                style = MaterialTheme.typography.labelMedium,
            )
        },
        colors = AssistChipDefaults.assistChipColors(
            containerColor =
                MaterialTheme.colorScheme.surfaceVariant,
            labelColor =
                MaterialTheme.colorScheme.onSurfaceVariant,
        ),
        border = null,
    )
}

@Preview(showBackground = true)
@Composable
private fun IngredientSuggestionChipPreview() {
    ChefIATheme {
        IngredientSuggestionChip(
            name = "Cebola",
            emoji = "🧅",
            onClick = {},
        )
    }
}