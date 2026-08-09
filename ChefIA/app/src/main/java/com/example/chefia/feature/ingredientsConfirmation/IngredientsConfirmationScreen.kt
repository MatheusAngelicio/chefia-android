package com.example.chefia.feature.ingredientsConfirmation

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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowForward
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.CameraAlt
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Eco
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.example.chefia.core.designsystem.components.ChefIAButton
import com.example.chefia.core.designsystem.components.ChefIABottomSheet
import com.example.chefia.core.designsystem.components.ChefIATopBar
import com.example.chefia.core.designsystem.components.ServingsSelector
import com.example.chefia.core.designsystem.theme.ChefIAColors
import com.example.chefia.core.designsystem.theme.ChefIATheme
import com.example.chefia.core.designsystem.theme.spacing
import com.example.chefia.feature.ingredientsConfirmation.components.AddIngredientBottomSheetContent
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IngredientsConfirmationScreen(
    ingredients: List<String>,
    photoPath: String,
    onBackClick: () -> Unit,
    onConfirmClick: (List<String>, Int) -> Unit,
    viewModel: IngredientsConfirmationViewModel = koinViewModel { parametersOf(ingredients, photoPath) },
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    IngredientsConfirmationContent(
        state = state,
        onAction = { action ->
            when (action) {
                IngredientsConfirmationAction.Confirm -> onConfirmClick(state.ingredients, state.servings)
                else -> viewModel.onAction(action)
            }
        },
        onBackClick = onBackClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun IngredientsConfirmationContent(
    state: IngredientsConfirmationUiState,
    onAction: (IngredientsConfirmationAction) -> Unit,
    onBackClick: () -> Unit,
) {
    val spacing = MaterialTheme.spacing

    Scaffold(
        topBar = {
            ChefIATopBar(onBackClick = onBackClick)
        },
    ) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = spacing.lg)
            ) {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(spacing.md)
                ) {
                    item {
                        Text(
                            text = "Ingredientes Detectados",
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            color = ChefIAColors.TextPrimary
                        )
                        Text(
                            text = "Confirme o que encontramos na sua geladeira para sugerirmos a melhor receita.",
                            style = MaterialTheme.typography.bodyLarge,
                            color = ChefIAColors.TextSecondary,
                            modifier = Modifier.padding(top = spacing.xs)
                        )
                    }

                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                                .clip(RoundedCornerShape(16.dp))
                        ) {
                            AsyncImage(
                                model = state.photoPath,
                                contentDescription = "Foto capturada",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                            
                            Row(
                                modifier = Modifier
                                    .align(Alignment.BottomStart)
                                    .padding(spacing.sm)
                                    .background(Color.Black.copy(alpha = 0.6f), RoundedCornerShape(8.dp))
                                    .padding(horizontal = 8.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Rounded.CameraAlt,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(16.dp)
                                )
                                Text(
                                    text = "Foto recente",
                                    color = Color.White,
                                    style = MaterialTheme.typography.labelSmall
                                )
                            }
                        }
                    }

                    item {
                        ServingsSelector(
                            servings = state.servings,
                            onServingsChange = { servings ->
                                onAction(IngredientsConfirmationAction.ServingsChanged(servings))
                            },
                            modifier = Modifier.padding(top = spacing.md)
                        )
                    }

                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Minha Lista",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold
                            )
                            
                            Box(
                                modifier = Modifier
                                    .background(ChefIAColors.AccentGreen.copy(alpha = 0.2f), RoundedCornerShape(16.dp))
                                    .padding(horizontal = 12.dp, vertical = 4.dp)
                            ) {
                                Text(
                                    text = "${state.ingredients.size} Itens",
                                    color = ChefIAColors.OnAccentGreen,
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }

                    items(state.ingredients) { ingredient ->
                        IngredientItem(
                            name = ingredient,
                            onRemove = { onAction(IngredientsConfirmationAction.RemoveIngredient(ingredient)) }
                        )
                    }

                    item {
                        TextButton(
                            onClick = { onAction(IngredientsConfirmationAction.AddManualIngredientClicked) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp)
                                .background(ChefIAColors.Secondary.copy(alpha = 0.2f), RoundedCornerShape(12.dp)),
                            colors = ButtonDefaults.textButtonColors(contentColor = ChefIAColors.OnSecondary)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Icon(Icons.Rounded.Add, contentDescription = null)
                                Text(text = "Adicionar mais manualmente", fontWeight = FontWeight.Medium)
                            }
                        }
                    }
                }

                ChefIAButton(
                    text = "Confirmar e Encontrar Receitas",
                    onClick = { onAction(IngredientsConfirmationAction.Confirm) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = spacing.lg),
                    trailingIcon = {
                        Icon(Icons.AutoMirrored.Rounded.ArrowForward, contentDescription = null)
                    }
                )
            }

            if (state.isAddIngredientSheetOpen) {
                ChefIABottomSheet(
                    onDismissRequest = { onAction(IngredientsConfirmationAction.DismissAddIngredientSheet) },
                    title = "Adicionar Ingrediente"
                ) {
                    AddIngredientBottomSheetContent(
                        value = state.manualIngredientInput,
                        onValueChanged = { onAction(IngredientsConfirmationAction.ManualIngredientChanged(it)) },
                        onAddClicked = { onAction(IngredientsConfirmationAction.SaveManualIngredient) }
                    )
                }
            }
        }
    }
}

@Composable
private fun IngredientItem(
    name: String,
    onRemove: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(ChefIAColors.AccentGreen.copy(alpha = 0.1f), RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Rounded.Eco,
                    contentDescription = null,
                    tint = ChefIAColors.OnAccentGreen
                )
            }
            
            Text(
                text = name,
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.bodyLarge,
                color = ChefIAColors.TextPrimary,
                fontWeight = FontWeight.Medium
            )
            
            IconButton(onClick = onRemove) {
                Icon(
                    imageVector = Icons.Rounded.Close,
                    contentDescription = "Remover",
                    tint = ChefIAColors.TextSecondary,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun IngredientsConfirmationPreview() {
    ChefIATheme {
        IngredientsConfirmationContent(
            state = IngredientsConfirmationUiState(
                ingredients = listOf("Tomate", "Ovos", "Alface", "Leite"),
                photoPath = ""
            ),
            onAction = {},
            onBackClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun IngredientsConfirmationBottomSheetPreview() {
    ChefIATheme {
        IngredientsConfirmationContent(
            state = IngredientsConfirmationUiState(
                ingredients = listOf("Tomate", "Ovos", "Alface", "Leite"),
                photoPath = "",
                isAddIngredientSheetOpen = true,
            ),
            onAction = {},
            onBackClick = {}
        )
    }
}

