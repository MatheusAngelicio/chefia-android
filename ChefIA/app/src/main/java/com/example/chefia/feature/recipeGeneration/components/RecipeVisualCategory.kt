package com.example.chefia.feature.recipeGeneration.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.chefia.domain.model.Recipe
import java.util.Locale

enum class RecipeVisualCategory {
    PASTA,
    RICE,
    SALAD,
    SOUP,
    BREAKFAST,
    MEAT,
    DESSERT,
    OTHER,
}

data class RecipeVisualStyle(
    val emoji: String,
    val label: String,
    val backgroundColor: Color,
    val contentColor: Color,
)

@Composable
fun Recipe.visualStyle(): RecipeVisualStyle {
    val category = resolveVisualCategory()

    return when (category) {
        RecipeVisualCategory.PASTA -> RecipeVisualStyle(
            emoji = "🍝",
            label = "MASSAS",
            backgroundColor =
                MaterialTheme.colorScheme.secondary.copy(
                    alpha = 0.18f,
                ),
            contentColor = MaterialTheme.colorScheme.onSecondary,
        )

        RecipeVisualCategory.RICE -> RecipeVisualStyle(
            emoji = "🍚",
            label = "ARROZ E RISOTOS",
            backgroundColor =
                MaterialTheme.colorScheme.primary.copy(
                    alpha = 0.12f,
                ),
            contentColor = MaterialTheme.colorScheme.primary,
        )

        RecipeVisualCategory.SALAD -> RecipeVisualStyle(
            emoji = "🥗",
            label = "SALADAS",
            backgroundColor =
                MaterialTheme.colorScheme.primaryContainer.copy(
                    alpha = 0.45f,
                ),
            contentColor = MaterialTheme.colorScheme.primary,
        )

        RecipeVisualCategory.SOUP -> RecipeVisualStyle(
            emoji = "🍲",
            label = "SOPAS E CALDOS",
            backgroundColor =
                MaterialTheme.colorScheme.secondary.copy(
                    alpha = 0.16f,
                ),
            contentColor = MaterialTheme.colorScheme.onSecondary,
        )

        RecipeVisualCategory.BREAKFAST -> RecipeVisualStyle(
            emoji = "🍳",
            label = "CAFÉ DA MANHÃ",
            backgroundColor =
                MaterialTheme.colorScheme.surfaceVariant,
            contentColor =
                MaterialTheme.colorScheme.onSurfaceVariant,
        )

        RecipeVisualCategory.MEAT -> RecipeVisualStyle(
            emoji = "🥩",
            label = "CARNES",
            backgroundColor =
                MaterialTheme.colorScheme.error.copy(
                    alpha = 0.10f,
                ),
            contentColor = MaterialTheme.colorScheme.error,
        )

        RecipeVisualCategory.DESSERT -> RecipeVisualStyle(
            emoji = "🍰",
            label = "SOBREMESAS",
            backgroundColor =
                MaterialTheme.colorScheme.secondary.copy(
                    alpha = 0.16f,
                ),
            contentColor = MaterialTheme.colorScheme.onSecondary,
        )

        RecipeVisualCategory.OTHER -> RecipeVisualStyle(
            emoji = "🍽️",
            label = "RECEITA ESPECIAL",
            backgroundColor =
                MaterialTheme.colorScheme.surfaceVariant,
            contentColor =
                MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}

private fun Recipe.resolveVisualCategory(): RecipeVisualCategory {
    val content = "$name $description"
        .lowercase(Locale.ROOT)

    return when {
        listOf(
            "macarrão",
            "massa",
            "pasta",
            "espaguete",
            "lasanha",
            "nhoque",
        ).any(content::contains) -> RecipeVisualCategory.PASTA

        listOf(
            "arroz",
            "risoto",
        ).any(content::contains) -> RecipeVisualCategory.RICE

        listOf(
            "salada",
            "vinagrete",
        ).any(content::contains) -> RecipeVisualCategory.SALAD

        listOf(
            "sopa",
            "caldo",
            "creme",
        ).any(content::contains) -> RecipeVisualCategory.SOUP

        listOf(
            "omelete",
            "ovo",
            "panqueca",
            "tapioca",
        ).any(content::contains) -> RecipeVisualCategory.BREAKFAST

        listOf(
            "carne",
            "frango",
            "peixe",
            "porco",
        ).any(content::contains) -> RecipeVisualCategory.MEAT

        listOf(
            "bolo",
            "doce",
            "sobremesa",
            "pudim",
        ).any(content::contains) -> RecipeVisualCategory.DESSERT

        else -> RecipeVisualCategory.OTHER
    }
}