package com.example.chefia.feature.ingredientsConfirmation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.chefia.core.designsystem.theme.ChefIAColors
import com.example.chefia.feature.ingredients.components.IngredientInput

@Composable
fun AddIngredientBottomSheetContent(
    value: String,
    onValueChanged: (String) -> Unit,
    onAddClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .padding(bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Digite o nome do ingrediente que você deseja adicionar.",
            style = MaterialTheme.typography.bodyMedium,
            color = ChefIAColors.TextSecondary
        )

        IngredientInput(
            value = value,
            onValueChanged = onValueChanged,
            onAddClicked = onAddClicked,
            modifier = Modifier.padding(top = 8.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))
    }
}
