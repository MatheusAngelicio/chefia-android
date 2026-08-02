package com.example.chefia.feature.ingredients.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.chefia.core.designsystem.theme.ChefIATheme
import com.example.chefia.core.designsystem.theme.spacing

@Composable
fun IngredientsPreferences(
    isFitness: Boolean,
    isBudget: Boolean,
    onFitnessToggled: (Boolean) -> Unit,
    onBudgetToggled: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    val spacing = MaterialTheme.spacing

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(spacing.sm),
    ) {
        Text(
            text = "PREFERÊNCIAS",
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )

        PreferenceItem(
            label = "Receita fitness",
            isChecked = isFitness,
            onCheckedChange = onFitnessToggled,
        )

        PreferenceItem(
            label = "Receita barata",
            isChecked = isBudget,
            onCheckedChange = onBudgetToggled,
        )
    }
}

@Composable
private fun PreferenceItem(
    label: String,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface,
        )

        Switch(
            checked = isChecked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = MaterialTheme.colorScheme.primary,
                checkedTrackColor = MaterialTheme.colorScheme.primaryContainer,
                uncheckedThumbColor = MaterialTheme.colorScheme.outline,
                uncheckedTrackColor = MaterialTheme.colorScheme.surfaceVariant,
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun IngredientsPreferencesPreview() {
    ChefIATheme {
        IngredientsPreferences(
            isFitness = true,
            isBudget = false,
            onFitnessToggled = {},
            onBudgetToggled = {},
            modifier = Modifier.padding(MaterialTheme.spacing.lg)
        )
    }
}
