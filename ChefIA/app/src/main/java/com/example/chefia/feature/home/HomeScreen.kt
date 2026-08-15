package com.example.chefia.feature.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CameraAlt
import androidx.compose.material.icons.automirrored.rounded.Logout
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.chefia.core.designsystem.theme.ChefIATheme
import com.example.chefia.core.designsystem.theme.spacing
import com.example.chefia.domain.model.Recipe
import com.example.chefia.domain.model.RecipeDifficulty
import com.example.chefia.feature.home.components.HomeActionCard
import com.example.chefia.feature.home.components.HomeActionCardOrientation
import com.example.chefia.feature.home.components.HomeFavoriteCard
import com.example.chefia.feature.home.components.HomeFavoritesEmptyState
import com.example.chefia.feature.home.components.HomeLogoutBottomSheet
import com.example.chefia.core.designsystem.components.ChefIATopBar
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    onNavigateToIngredients: () -> Unit,
    onNavigateToCamera: () -> Unit,
    onRecipeClick: (Recipe) -> Unit,
    onViewAllFavoritesClick: () -> Unit,
    onLogout: () -> Unit,
    viewModel: HomeViewModel = koinViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(state.isLoggedOut) {
        if (state.isLoggedOut) {
            onLogout()
        }
    }

    Scaffold(
        topBar = {
            ChefIATopBar(
                actions = {
                    IconButton(onClick = { viewModel.onAction(HomeAction.LogoutRequest) }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.Logout,
                            contentDescription = "Sair",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            )
        },
    ) { innerPadding ->

        HomeContent(
            state = state,
            modifier = Modifier.padding(innerPadding),
            onAction = { action ->
                when (action) {
                    HomeAction.CameraClicked -> onNavigateToCamera()
                    HomeAction.TypeIngredientsClicked -> onNavigateToIngredients()
                    is HomeAction.RecipeClicked -> onRecipeClick(action.recipe)
                    HomeAction.ViewAllFavoritesClicked -> onViewAllFavoritesClick()
                    HomeAction.LogoutRequest -> {} // Handled in VM
                    HomeAction.LogoutConfirm -> {} // Handled in VM
                    HomeAction.LogoutDismiss -> {} // Handled in VM
                }
                viewModel.onAction(action)
            },
        )

        if (state.showLogoutConfirmation) {
            HomeLogoutBottomSheet(
                onConfirmLogout = { viewModel.onAction(HomeAction.LogoutConfirm) },
                onDismiss = { viewModel.onAction(HomeAction.LogoutDismiss) }
            )
        }
    }
}

@Composable
private fun HomeContent(
    state: HomeUiState,
    modifier: Modifier = Modifier,
    onAction: (HomeAction) -> Unit,
) {
    val spacing = MaterialTheme.spacing

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(
                vertical = spacing.lg,
            ),
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = spacing.lg)
        ) {
            Text(
                text = "O que vamos cozinhar hoje?",
                style = MaterialTheme.typography.headlineLarge,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onBackground,
            )

            Text(
                text = "Informe os ingredientes que você tem e a ChefIA encontrará as melhores receitas.",
                modifier = Modifier.padding(
                    top = spacing.md,
                    bottom = spacing.xxl,
                ),
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(spacing.md),
            ) {
                HomeActionCard(
                    title = "Tirar foto da geladeira",
                    icon = Icons.Rounded.CameraAlt,
                    backgroundColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                    iconBackgroundColor = MaterialTheme.colorScheme.primaryContainer,
                    onClick = {
                        onAction(HomeAction.CameraClicked)
                    },
                )

                HomeActionCard(
                    title = "Digitar ingredientes",
                    icon = Icons.Rounded.Edit,
                    backgroundColor = MaterialTheme.colorScheme.secondary,
                    contentColor = MaterialTheme.colorScheme.onSecondary,
                    iconBackgroundColor = MaterialTheme.colorScheme.secondaryContainer,
                    orientation = HomeActionCardOrientation.Horizontal,
                    onClick = {
                        onAction(HomeAction.TypeIngredientsClicked)
                    },
                )
            }
        }

        Spacer(modifier = Modifier.height(spacing.xxl))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = spacing.lg),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "Favoritos",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground,
            )

            TextButton(
                onClick = { onAction(HomeAction.ViewAllFavoritesClicked) }
            ) {
                Text(text = "Ver tudo")
            }
        }

        if (state.favoriteRecipes.isNotEmpty()) {
            LazyRow(
                contentPadding = PaddingValues(horizontal = spacing.lg),
                horizontalArrangement = Arrangement.spacedBy(spacing.md),
            ) {
                items(
                    items = state.favoriteRecipes,
                    key = { it.id },
                ) { recipe ->
                    HomeFavoriteCard(
                        recipe = recipe,
                        onClick = { onAction(HomeAction.RecipeClicked(recipe)) }
                    )
                }
            }
        } else {
            HomeFavoritesEmptyState(
                modifier = Modifier.padding(horizontal = spacing.lg)
            )
        }

        Spacer(modifier = Modifier.height(spacing.sm))
    }
}


@Preview(
    showBackground = true,
    showSystemUi = true,
)
@Composable
private fun HomeContentPreview() {
    ChefIATheme {
        HomeContent(
            state = HomeUiState(
                favoriteRecipes = listOf(
                    Recipe(
                        id = "1",
                        name = "Omelete de Queijo",
                        description = "Um omelete rápido e fácil",
                        preparationTimeMinutes = 10,
                        servings = 1,
                        caloriesPerServingKcal = 200,
                        difficulty = RecipeDifficulty.EASY,
                        ingredients = emptyList(),
                        preparationSteps = emptyList()
                    )
                )
            ),
            onAction = {},
        )
    }
}