package com.example.chefia.feature.recipeExecution

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Timer
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.chefia.core.designsystem.components.ChefIAButton
import com.example.chefia.core.designsystem.components.ChefIAProgressIndicator
import com.example.chefia.core.designsystem.components.ChefIATopBar
import com.example.chefia.core.designsystem.theme.ChefIAColors
import com.example.chefia.core.designsystem.theme.ChefIATheme
import com.example.chefia.core.designsystem.theme.spacing
import com.example.chefia.domain.model.Recipe
import com.example.chefia.domain.model.RecipeDifficulty
import com.example.chefia.domain.model.RecipeIngredient
import com.example.chefia.domain.model.RecipeStep
import com.example.chefia.feature.recipeExecution.components.RecipeExecutionImageHeader
import com.example.chefia.feature.recipeExecution.components.RecipeExecutionStepCard

@Composable
fun RecipeExecutionScreen(
    recipe: Recipe,
    onBackClick: () -> Unit,
    viewModel: RecipeExecutionViewModel = viewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val spacing = MaterialTheme.spacing

    LaunchedEffect(recipe) {
        viewModel.init(recipe)
    }

    Scaffold(
        topBar = {
            ChefIATopBar(
                title = recipe.name,
                onBackClick = onBackClick,
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Rounded.Close,
                            contentDescription = "Sair da receita",
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /* TODO */ }) {
                        Icon(
                            imageVector = Icons.Rounded.Timer,
                            contentDescription = "Timer",
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }
            )
        },
        bottomBar = {
            Column {
                HorizontalDivider(
                    thickness = 1.dp,
                    color = ChefIAColors.Border
                )
                Surface(
                    color = MaterialTheme.colorScheme.background,
                ) {
                    Row(
                        modifier = Modifier
                            .padding(spacing.lg)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Anterior
                        Row(
                            modifier = Modifier
                                .weight(1f)
                                .clickable(enabled = !uiState.isFirstStep) {
                                    viewModel.onAction(RecipeExecutionAction.PreviousStep)
                                },
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowLeft,
                                contentDescription = null,
                                tint = if (uiState.isFirstStep) Color.LightGray else Color.Gray,
                                modifier = Modifier.size(24.dp)
                            )
                            Text(
                                text = "Anterior",
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    fontWeight = FontWeight.Bold
                                ),
                                color = if (uiState.isFirstStep) Color.LightGray else Color.Gray
                            )
                        }

                        // Próximo Passo
                        ChefIAButton(
                            text = if (uiState.isLastStep) "Finalizar" else "Próximo Passo",
                            onClick = {
                                if (uiState.isLastStep) {
                                    onBackClick()
                                } else {
                                    viewModel.onAction(RecipeExecutionAction.NextStep)
                                }
                            },
                            modifier = Modifier.weight(1.5f),
                            trailingIcon = {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowRight,
                                    contentDescription = null,
                                    tint = Color.White
                                )
                            }
                        )
                    }
                }
            }
        },
    ) { innerPadding ->
        RecipeExecutionContent(
            uiState = uiState,
            modifier = Modifier.padding(innerPadding),
        )
    }
}

@Composable
private fun RecipeExecutionContent(
    uiState: RecipeExecutionUiState,
    modifier: Modifier = Modifier,
) {
    val spacing = MaterialTheme.spacing

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = spacing.lg),
        verticalArrangement = Arrangement.spacedBy(spacing.lg),
    ) {
        // Progress Indicator
        Column(
            modifier = Modifier.padding(top = spacing.md),
            verticalArrangement = Arrangement.spacedBy(spacing.sm)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = "PASSO ${uiState.currentStepIndex + 1} DE ${uiState.totalSteps}",
                    style = MaterialTheme.typography.labelLarge.copy(
                        fontWeight = FontWeight.ExtraBold,
                        color = Color(0xFF1B5E20)
                    )
                )
                Text(
                    text = "${(uiState.progress * 100).toInt()}% concluído",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            ChefIAProgressIndicator(
                progress = uiState.progress,
                modifier = Modifier
                    .fillMaxWidth(),
                color = Color(0xFF1B5E20),
                trackColor = Color(0xFFE8F5E9),
                height = 8.dp
            )
        }

        RecipeExecutionImageHeader(
            imageUrl = uiState.recipe?.imageUrl
        )

        // Step Content
        AnimatedContent(
            targetState = uiState.currentStep,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            transitionSpec = {
                (slideInHorizontally { width -> width } + fadeIn())
                    .togetherWith(slideOutHorizontally { width -> -width } + fadeOut())
            },
            label = "StepTransition",
        ) { step ->
            if (step != null) {
                RecipeExecutionStepCard(
                    step = step,
                    stepNumber = uiState.currentStepIndex + 1,
                    modifier = Modifier.verticalScroll(rememberScrollState())
                )
            }
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
)
@Composable
private fun RecipeExecutionScreenPreview() {
    ChefIATheme {
        RecipeExecutionScreen(
            recipe = Recipe(
                id = "1",
                name = "Omelete de Queijo",
                description = "Um omelete rápido e fácil",
                preparationTimeMinutes = 10,
                servings = 1,
                caloriesPerServingKcal = 200,
                difficulty = RecipeDifficulty.EASY,
                ingredients = listOf(
                    RecipeIngredient("2 filés de Salmão Fresco", "400g", true),
                ),
                preparationSteps = listOf(
                    RecipeStep(
                        "PREPARAÇÃO",
                        "Lave bem o brócolis e corte em floretes pequenos. Tempere o salmão levemente com sal e pimenta-do-reino (o molho já é salgado)."
                    ),
                    RecipeStep(
                        "FINALIZAÇÃO",
                        "Sirva quente com uma fatia de pão integral."
                    ),
                )
            ),
            onBackClick = {},
        )
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
)
@Composable
private fun RecipeExecutionContentPreview() {
    ChefIATheme {
        RecipeExecutionContent(
            uiState = RecipeExecutionUiState(
                recipe = Recipe(
                    id = "1",
                    name = "Omelete de Queijo",
                    description = "Um omelete rápido e fácil",
                    preparationTimeMinutes = 10,
                    servings = 1,
                    caloriesPerServingKcal = 200,
                    difficulty = RecipeDifficulty.EASY,
                    ingredients = listOf(
                        RecipeIngredient("2 filés de Salmão Fresco", "400g", true),
                    ),
                    preparationSteps = listOf(
                        RecipeStep(
                            "PREPARAÇÃO",
                            "Lave bem o brócolis e corte em floretes pequenos. Tempere o salmão levemente com sal e pimenta-do-reino (o molho já é salgado)."
                        ),
                        RecipeStep(
                            "FINALIZAÇÃO",
                            "Sirva quente com uma fatia de pão integral."
                        ),
                    )
                ),
                currentStepIndex = 0
            )
        )
    }
}
