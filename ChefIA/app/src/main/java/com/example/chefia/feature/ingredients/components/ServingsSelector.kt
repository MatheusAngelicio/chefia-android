package com.example.chefia.feature.ingredients.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Remove
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chefia.core.designsystem.theme.ChefIATheme
import com.example.chefia.core.designsystem.theme.spacing

@Composable
fun ServingsSelector(
    servings: Int,
    onServingsChange: (Int) -> Unit,
    modifier: Modifier = Modifier,
    minServings: Int = 1,
    maxServings: Int = 20,
) {
    require(minServings > 0) {
        "minServings deve ser maior que zero."
    }

    require(maxServings >= minServings) {
        "maxServings deve ser maior ou igual a minServings."
    }

    val spacing = MaterialTheme.spacing
    val canDecrease = servings > minServings
    val canIncrease = servings < maxServings

    Column(
        modifier = modifier.fillMaxWidth(),
    ) {
        Text(
            text = "QUANTIDADE DE PORÇÕES",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            letterSpacing = 1.sp,
        )

        Text(
            text = "Para quantas pessoas você vai cozinhar?",
            modifier = Modifier.padding(top = spacing.md),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface,
        )

        Surface(
            modifier = Modifier
                .padding(top = spacing.lg)
                .fillMaxWidth(0.63f)
                .height(80.dp)
                .align(Alignment.Start),
            shape = RoundedCornerShape(22.dp),
            color = MaterialTheme.colorScheme.surface,
            border = BorderStroke(
                width = 2.dp,
                color = MaterialTheme.colorScheme.primary.copy(
                    alpha = 0.25f,
                ),
            ),
        ) {
            Row(
                modifier = Modifier.padding(horizontal = spacing.lg),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                ServingsButton(
                    icon = Icons.Rounded.Remove,
                    contentDescription = "Diminuir quantidade de porções",
                    enabled = canDecrease,
                    onClick = {
                        onServingsChange(servings - 1)
                    },
                )

                Text(
                    text = servings.toString(),
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                )

                ServingsButton(
                    icon = Icons.Rounded.Add,
                    contentDescription = "Aumentar quantidade de porções",
                    enabled = canIncrease,
                    isPrimary = true,
                    onClick = {
                        onServingsChange(servings + 1)
                    },
                )
            }
        }
    }
}

@Composable
private fun ServingsButton(
    icon: ImageVector,
    contentDescription: String,
    enabled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isPrimary: Boolean = false,
) {
    val containerColor = if (isPrimary) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.surfaceVariant
    }

    val contentColor = if (isPrimary) {
        MaterialTheme.colorScheme.onPrimary
    } else {
        MaterialTheme.colorScheme.primary
    }

    FilledIconButton(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier.size(58.dp),
        shape = RoundedCornerShape(14.dp),
        colors = IconButtonDefaults.filledIconButtonColors(
            containerColor = containerColor,
            contentColor = contentColor,
            disabledContainerColor = containerColor.copy(alpha = 0.65f),
            disabledContentColor = contentColor.copy(alpha = 0.55f),
        ),
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            modifier = Modifier.size(30.dp),
        )
    }
}


@Preview(showBackground = true)
@Composable
private fun ServingsSelectorPreview() {
    ChefIATheme {
        ServingsSelector(
            servings = 1,
            onServingsChange = {},
            modifier = Modifier.padding(24.dp),
        )
    }
}