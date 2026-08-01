package com.example.chefia.feature.recipe

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.chefia.core.common.UiText
import com.example.chefia.core.designsystem.components.ChefIAButton
import com.example.chefia.core.designsystem.theme.ChefIATheme
import com.example.chefia.core.designsystem.theme.spacing
import com.example.chefia.domain.model.Recipe
import com.example.chefia.domain.model.RecipeDifficulty
import com.example.chefia.feature.recipe.components.RecipeLoadingAnimation
import org.koin.androidx.compose.koinViewModel

@Composable
fun RecipeGenerationScreen(
    ingredients: List<String>,
    viewModel: RecipeGenerationViewModel = koinViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(ingredients) {
        viewModel.onAction(
            RecipeGenerationAction.GenerateRecipes(
                ingredients = ingredients,
            ),
        )
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
    ) { innerPadding ->
        when (val status = state.status) {
            RecipeGenerationStatus.Loading -> {
                RecipeLoadingContent(
                    state = state,
                    modifier = Modifier.padding(innerPadding),
                )
            }

            is RecipeGenerationStatus.Success -> {
                RecipeGeneratedContent(
                    recipes = status.recipes,
                    modifier = Modifier.padding(innerPadding),
                )
            }

            is RecipeGenerationStatus.Error -> {
                RecipeGenerationErrorContent(
                    message = status.message,
                    onRetry = {
                        viewModel.onAction(
                            RecipeGenerationAction.RetryClicked,
                        )
                    },
                    modifier = Modifier.padding(innerPadding),
                )
            }
        }
    }
}

@Composable
private fun RecipeLoadingContent(
    state: RecipeGenerationUiState,
    modifier: Modifier = Modifier,
) {
    val spacing = MaterialTheme.spacing

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = spacing.xl),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(
            modifier = Modifier.weight(0.18f),
        )

        LoadingIllustration(
            currentIngredient = state.currentIngredient,
        )

        Spacer(
            modifier = Modifier.height(spacing.xxl),
        )

        Text(
            text = "Analisando seus\ningredientes...",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center,
        )

        Text(
            text = "Nossa IA está criando a receita perfeita com o que você tem na geladeira.",
            modifier = Modifier.padding(
                top = spacing.md,
                start = spacing.sm,
                end = spacing.sm,
            ),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
        )

        Spacer(
            modifier = Modifier.weight(0.28f),
        )
    }
}

@Composable
private fun LoadingIllustration(
    currentIngredient: String,
    modifier: Modifier = Modifier,
) {
    val primaryColor = MaterialTheme.colorScheme.primary
    val strokeWidth = 3.dp

    val infiniteTransition = rememberInfiniteTransition(label = "BorderRotation")
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 15000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "Rotation"
    )

    Box(
        modifier = modifier
            .fillMaxWidth(0.82f)
            .aspectRatio(1f)
            .drawBehind {
                rotate(rotation) {
                    drawCircle(
                        color = primaryColor,
                        style = Stroke(
                            width = strokeWidth.toPx(),
                            pathEffect = PathEffect.dashPathEffect(
                                intervals = floatArrayOf(24f, 24f),
                                phase = 0f,
                            ),
                        ),
                    )
                }
            },
        contentAlignment = Alignment.Center,
    ) {
        RecipeLoadingAnimation(
            modifier = Modifier.fillMaxSize(0.85f),
        )

        IngredientLoadingLabel(
            text = currentIngredient,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .offset(
                    x = 4.dp,
                    y = (-14).dp,
                ),
        )
    }
}

@Composable
private fun IngredientLoadingLabel(
    text: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = text,
        modifier = modifier
            .rotate(-6f)
            .background(
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = RoundedCornerShape(50),
            )
            .padding(
                horizontal = MaterialTheme.spacing.lg,
                vertical = MaterialTheme.spacing.xs,
            ),
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.onPrimary,
        maxLines = 1,
    )
}

@Composable
private fun RecipeGeneratedContent(
    recipes: List<Recipe>,
    modifier: Modifier = Modifier,
) {
    val spacing = MaterialTheme.spacing

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(spacing.xl),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Receitas prontas! 🍳",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center,
        )

        recipes.forEach { recipe ->
            Text(
                text = "• ${recipe.name}",
                modifier = Modifier.padding(top = spacing.md),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Composable
private fun RecipeGenerationErrorContent(
    message: UiText,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val spacing = MaterialTheme.spacing

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(spacing.xl),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Não conseguimos gerar as receitas",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.error,
            textAlign = TextAlign.Center,
        )

        Text(
            text = message.asString(),
            modifier = Modifier.padding(
                top = spacing.md,
                bottom = spacing.lg,
            ),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
        )

        ChefIAButton(
            text = "Tentar novamente",
            onClick = onRetry,
        )
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
)
@Composable
private fun RecipeLoadingContentPreview() {
    ChefIATheme {
        RecipeLoadingContent(
            state = RecipeGenerationUiState(),
        )
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
)
@Composable
private fun RecipeSuccessContentPreview() {
    ChefIATheme {
        RecipeGeneratedContent(
            recipes = listOf(
                Recipe(
                    id = "1",
                    name = "Omelete de Queijo",
                    description = "Um omelete rápido e fácil",
                    preparationTimeMinutes = 10,
                    servings = 1,
                    difficulty = RecipeDifficulty.EASY,
                    ingredients = emptyList(),
                    preparationSteps = emptyList()
                )
            ),
        )
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
)
@Composable
private fun RecipeErrorContentPreview() {
    ChefIATheme {
        RecipeGenerationErrorContent(
            message = UiText.DynamicString("Não foi possível conectar ao servidor. Verifique sua conexão."),
            onRetry = {}
        )
    }
}