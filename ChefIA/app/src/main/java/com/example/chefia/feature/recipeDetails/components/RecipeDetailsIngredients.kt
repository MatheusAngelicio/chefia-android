package com.example.chefia.feature.recipeDetails.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.chefia.core.designsystem.theme.ChefIAColors
import com.example.chefia.core.designsystem.theme.ChefIATheme
import com.example.chefia.core.designsystem.theme.spacing
import com.example.chefia.domain.model.RecipeIngredient

@Composable
fun RecipeDetailsIngredients(
    ingredients: List<RecipeIngredient>,
    modifier: Modifier = Modifier,
) {
    val spacing = MaterialTheme.spacing

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = spacing.lg),
        verticalArrangement = Arrangement.spacedBy(spacing.md),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "Ingredientes",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = "${ingredients.size} itens no total",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }

        ingredients.forEach { ingredient ->
            IngredientItem(ingredient = ingredient)
        }
    }
}

@Composable
private fun IngredientItem(
    ingredient: RecipeIngredient,
    modifier: Modifier = Modifier,
) {
    val spacing = MaterialTheme.spacing
    
    val backgroundColor = if (ingredient.isAvailable) {
        ChefIAColors.IngredientAvailableBackground
    } else {
        ChefIAColors.IngredientMissingBackground
    }
    
    val contentColor = if (ingredient.isAvailable) {
        MaterialTheme.colorScheme.onSurface
    } else {
        ChefIAColors.IngredientMissingText
    }
    
    val iconColor = if (ingredient.isAvailable) {
        ChefIAColors.IngredientAvailableIcon
    } else {
        ChefIAColors.IngredientMissingText
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(backgroundColor)
            .then(
                if (!ingredient.isAvailable) {
                    Modifier.border(1.dp, ChefIAColors.IngredientMissingBorder, RoundedCornerShape(12.dp))
                } else Modifier
            )
            .padding(spacing.md),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(spacing.md),
    ) {
        Icon(
            imageVector = if (ingredient.isAvailable) Icons.Rounded.CheckCircle else Icons.Rounded.ShoppingCart,
            contentDescription = null,
            tint = iconColor,
            modifier = Modifier.size(24.dp)
        )

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Text(
                text = ingredient.name,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium,
                color = contentColor,
            )

            Text(
                text = ingredient.quantity,
                style = MaterialTheme.typography.bodyMedium,
                color = contentColor.copy(alpha = 0.8f),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun RecipeDetailsIngredientsPreview() {
    ChefIATheme {
        RecipeDetailsIngredients(
            ingredients = listOf(
                RecipeIngredient("2 filés de Salmão Fresco", "400g", true),
                RecipeIngredient("Molho Teriyaki", "100ml", true),
                RecipeIngredient("Espetinho de carne", "1 unidade(aproximadamente 150g)", true),
                RecipeIngredient("Gergelim Branco", "10g", false),
                RecipeIngredient("Arroz Jasmine", "1 xícara", true),
            )
        )
    }
}
