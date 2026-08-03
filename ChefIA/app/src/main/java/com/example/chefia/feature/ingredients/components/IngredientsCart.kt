package com.example.chefia.feature.ingredients.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DeleteOutline
import androidx.compose.material.icons.rounded.Inventory2
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.chefia.core.designsystem.theme.ChefIATheme
import com.example.chefia.core.designsystem.theme.spacing

@Composable
fun IngredientsCart(
    ingredients: List<String>,
    onRemoveIngredient: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.sm),
    ) {
        ingredientsCart(
            ingredients = ingredients,
            onRemoveIngredient = onRemoveIngredient,
        )
    }
}

fun LazyListScope.ingredientsCart(
    ingredients: List<String>,
    onRemoveIngredient: (String) -> Unit,
) {
    if (ingredients.isEmpty()) {
        item {
            EmptyIngredientsCart(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 32.dp),
            )
        }
    } else {
        items(
            items = ingredients,
            key = { ingredient -> ingredient },
        ) { ingredient ->
            IngredientCartItem(
                ingredient = ingredient,
                onRemoveClicked = {
                    onRemoveIngredient(ingredient)
                },
            )
        }
    }
}

@Composable
private fun EmptyIngredientsCart(
    modifier: Modifier = Modifier,
) {
    val spacing = MaterialTheme.spacing

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier
                .size(92.dp)
                .background(
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    shape = CircleShape,
                ),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = Icons.Rounded.Inventory2,
                contentDescription = null,
                modifier = Modifier.size(40.dp),
                tint = MaterialTheme.colorScheme.outline,
            )
        }

        Text(
            text = "Sua lista está vazia.\nComece a digitar acima!",
            modifier = Modifier.padding(top = spacing.md),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.height(spacing.sm))
    }
}

@Composable
private fun IngredientCartItem(
    ingredient: String,
    onRemoveClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.surfaceVariant,
    ) {
        Row(
            modifier = Modifier.padding(
                horizontal = 16.dp,
                vertical = 8.dp,
            ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = ingredient,
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface,
            )

            IconButton(
                onClick = onRemoveClicked,
            ) {
                Icon(
                    imageVector = Icons.Rounded.DeleteOutline,
                    contentDescription = "Remover $ingredient",
                    tint = MaterialTheme.colorScheme.error,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun EmptyIngredientsCartPreview() {
    ChefIATheme {
        IngredientsCart(
            ingredients = emptyList(),
            onRemoveIngredient = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun IngredientsCartPreview() {
    ChefIATheme {
        IngredientsCart(
            ingredients = listOf(
                "Frango",
                "Batata",
                "Cebola",
            ),
            onRemoveIngredient = {},
        )
    }
}