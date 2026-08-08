package com.example.chefia.feature.recipeGeneration.components

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccessTime
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.icons.rounded.Groups
import androidx.compose.material.icons.rounded.LocalFireDepartment
import androidx.compose.material.icons.rounded.Restaurant
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.SubcomposeAsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.chefia.core.designsystem.components.ChefIAAiBadge
import com.example.chefia.core.designsystem.components.ChefIAButton
import com.example.chefia.core.designsystem.components.ChefIAProgressIndicator
import com.example.chefia.core.designsystem.theme.ChefIATheme
import com.example.chefia.core.designsystem.theme.spacing
import com.example.chefia.domain.model.Recipe
import com.example.chefia.domain.model.RecipeDifficulty
import com.example.chefia.domain.model.RecipeIngredient

@Composable
fun RecipeResultCard(
    recipe: Recipe,
    isFavorite: Boolean,
    onFavoriteClicked: () -> Unit,
    onRecipeClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val spacing = MaterialTheme.spacing
    val visualStyle = recipe.visualStyle()

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.extraLarge,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 3.dp,
        ),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .background(visualStyle.backgroundColor),
            contentAlignment = Alignment.Center
        ) {
            if (recipe.imageUrl == null) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    strokeWidth = 2.dp,
                    color = visualStyle.contentColor
                )
            } else {
                SubcomposeAsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(recipe.imageUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop,
                    loading = {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(24.dp),
                                strokeWidth = 2.dp,
                                color = visualStyle.contentColor
                            )
                        }
                    }
                )
            }

            ChefIAAiBadge(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(spacing.md),
            )

            Surface(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(spacing.md),
                color = MaterialTheme.colorScheme.surface.copy(alpha = 0.9f),
                shape = MaterialTheme.shapes.medium,
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = spacing.sm, vertical = spacing.xs),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(spacing.xs)
                ) {
                    Text(text = visualStyle.emoji)
                    Text(
                        text = visualStyle.label,
                        style = MaterialTheme.typography.labelLarge,
                        color = visualStyle.contentColor,
                    )
                }
            }

            IconButton(
                onClick = onFavoriteClicked,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(spacing.sm)
                    .background(
                        color = MaterialTheme.colorScheme.surface,
                        shape = CircleShape,
                    ),
            ) {
                Icon(
                    imageVector = if (isFavorite) {
                        Icons.Rounded.Favorite
                    } else {
                        Icons.Rounded.FavoriteBorder
                    },
                    contentDescription = if (isFavorite) {
                        "Remover dos favoritos"
                    } else {
                        "Adicionar aos favoritos"
                    },
                    tint = if (isFavorite) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.onSurfaceVariant
                    },
                )
            }
        }

        Column(
            modifier = Modifier.padding(spacing.lg),
        ) {
            Text(
                text = recipe.name,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface,
            )

            RecipeMetadata(
                recipe = recipe,
                modifier = Modifier.padding(top = spacing.sm),
            )

            RecipeCompatibility(
                recipe = recipe,
                modifier = Modifier.padding(top = spacing.lg),
            )

            Spacer(modifier = Modifier.height(spacing.lg))

            ChefIAButton(
                text = "Ver receita →",
                onClick = onRecipeClicked,
            )
        }
    }
}

@Composable
private fun RecipeMetadata(
    recipe: Recipe,
    modifier: Modifier = Modifier,
) {
    val spacing = MaterialTheme.spacing

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(spacing.sm),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(spacing.md),
        ) {
            RecipeMetadataItem(
                modifier = Modifier.weight(1f),
                icon = {
                    Icon(
                        imageVector = Icons.Rounded.AccessTime,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp),
                    )
                },
                text = "${recipe.preparationTimeMinutes} min",
            )

            RecipeMetadataItem(
                modifier = Modifier.weight(1f),
                icon = {
                    Icon(
                        imageVector = Icons.Rounded.Restaurant,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp),
                    )
                },
                text = recipe.difficultyToDisplayName,
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(spacing.md),
        ) {
            RecipeMetadataItem(
                modifier = Modifier.weight(1f),
                icon = {
                    Icon(
                        imageVector = Icons.Rounded.Groups,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp),
                    )
                },
                text = recipe.servingsToDisplay,
            )

            RecipeMetadataItem(
                modifier = Modifier.weight(1f),
                icon = {
                    Icon(
                        imageVector = Icons.Rounded.LocalFireDepartment,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp),
                    )
                },
                text = recipe.caloriesPerServingDisplay,
            )
        }
    }
}

@Composable
private fun RecipeMetadataItem(
    icon: @Composable () -> Unit,
    text: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(
            MaterialTheme.spacing.xs,
        ),
    ) {
        icon()

        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            maxLines = 1,
        )
    }
}

@Composable
private fun RecipeCompatibility(
    recipe: Recipe,
    modifier: Modifier = Modifier,
) {
    val spacing = MaterialTheme.spacing
    val compatibility =
        recipe.compatibilityPercentage / 100f

    val isHighCompatibility =
        recipe.compatibilityPercentage >= 75

    val highlightColor = if (isHighCompatibility) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.secondary
    }

    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.large,
        color = highlightColor.copy(alpha = 0.10f),
    ) {
        Column(
            modifier = Modifier.padding(spacing.md),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    imageVector = Icons.Rounded.CheckCircle,
                    contentDescription = null,
                    modifier = Modifier.size(22.dp),
                    tint = highlightColor,
                )

                Text(
                    text = "Você possui ${recipe.availableIngredientsCount} de ${recipe.totalIngredientsCount} ingredientes",
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = spacing.sm),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                )

                Text(
                    text = "${recipe.compatibilityPercentage}%",
                    style = MaterialTheme.typography.labelLarge,
                    color = highlightColor,
                )
            }

            ChefIAProgressIndicator(
                progress = compatibility,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = spacing.sm),
                color = highlightColor,
                trackColor = highlightColor.copy(alpha = 0.18f),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun RecipeResultCardPreview() {
    ChefIATheme {
        RecipeResultCard(
            recipe = Recipe(
                id = "1",
                name = "Risoto de Cogumelos",
                description = "Um risoto cremoso e fácil.",
                preparationTimeMinutes = 35,
                servings = 2,
                difficulty = RecipeDifficulty.EASY,
                ingredients = listOf(
                    RecipeIngredient(
                        name = "Arroz",
                        quantity = "1 xícara",
                        isAvailable = true,
                    ),
                    RecipeIngredient(
                        name = "Cogumelos",
                        quantity = "200 g",
                        isAvailable = true,
                    ),
                    RecipeIngredient(
                        name = "Queijo",
                        quantity = "100 g",
                        isAvailable = false,
                    ),
                ),
                preparationSteps = emptyList(),
                caloriesPerServingKcal = 300,
            ),
            isFavorite = false,
            onFavoriteClicked = {},
            onRecipeClicked = {},
            modifier = Modifier.padding(16.dp),
        )
    }
}