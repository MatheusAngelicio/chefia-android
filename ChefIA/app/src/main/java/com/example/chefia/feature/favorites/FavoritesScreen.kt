package com.example.chefia.feature.favorites

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Restaurant
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import com.example.chefia.feature.favorites.components.FavoriteRecipeCard
import com.example.chefia.feature.favorites.components.FavoritesSearchInput
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.chefia.R
import com.example.chefia.core.designsystem.components.ChefIAButton
import com.example.chefia.core.designsystem.components.ChefIATopBar
import com.example.chefia.core.designsystem.theme.ChefIAColors
import com.example.chefia.core.designsystem.theme.ChefIATheme
import com.example.chefia.core.designsystem.theme.spacing
import com.example.chefia.domain.model.Recipe
import com.example.chefia.domain.model.RecipeDifficulty
import org.koin.androidx.compose.koinViewModel

@Composable
fun FavoritesScreen(
    onBackClick: () -> Unit,
    onExploreClick: () -> Unit,
    onRecipeClick: (Recipe) -> Unit,
    viewModel: FavoritesViewModel = koinViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            ChefIATopBar(
                onBackClick = onBackClick,
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
                    ChefIAButton(
                        text = "Explorar Receitas",
                        onClick = onExploreClick,
                        modifier = Modifier
                            .padding(MaterialTheme.spacing.lg)
                            .fillMaxWidth(),
                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Rounded.Restaurant,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    )
                }
            }
        },
    ) { innerPadding ->
        FavoritesContent(
            state = state,
            onAction = { action ->
                when (action) {
                    FavoritesAction.BackClicked -> onBackClick()
                    FavoritesAction.ExploreRecipesClicked -> onExploreClick()
                    is FavoritesAction.RecipeClicked -> onRecipeClick(action.recipe)
                    else -> viewModel.onAction(action)
                }
            },
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
private fun FavoritesContent(
    state: FavoritesUiState,
    onAction: (FavoritesAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    if (state.recipes.isEmpty()) {
        FavoritesEmptyState(
            modifier = modifier
        )
    } else {
        FavoritesListContent(
            state = state,
            onAction = onAction,
            modifier = modifier
        )
    }
}

@Composable
private fun FavoritesListContent(
    state: FavoritesUiState,
    onAction: (FavoritesAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    val spacing = MaterialTheme.spacing

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = spacing.lg)
    ) {
        FavoritesSearchInput(
            query = state.searchQuery,
            onQueryChanged = { onAction(FavoritesAction.SearchQueryChanged(it)) },
            modifier = Modifier.padding(vertical = spacing.md)
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = spacing.xl),
            verticalArrangement = Arrangement.spacedBy(spacing.md)
        ) {
            items(
                items = state.filteredRecipes,
                key = { it.id }
            ) { recipe ->
                FavoriteRecipeCard(
                    recipe = recipe,
                    onRecipeClick = { onAction(FavoritesAction.RecipeClicked(recipe)) },
                    onFavoriteClick = { onAction(FavoritesAction.ToggleFavorite(recipe)) }
                )
            }
        }
    }
}

@Composable
private fun FavoritesEmptyState(
    modifier: Modifier = Modifier,
) {
    val spacing = MaterialTheme.spacing
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.empty_list))

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = spacing.xxl, vertical = spacing.lg),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "Favoritos",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center
        )

        Text(
            text = "Suas receitas salvas para cozinhar mais tarde.",
            modifier = Modifier.padding(top = spacing.xs),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(spacing.xxl))

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            // Lottie Animation
            LottieAnimation(
                composition = composition,
                iterations = 1,
                modifier = Modifier.size(240.dp)
            )

            // Heart Badge
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(end = spacing.lg)
                    .size(48.dp)
                    .background(
                        color = ChefIAColors.Primary,
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Rounded.Favorite,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(spacing.xxl))

        Text(
            text = "Você ainda não possui receitas favoritas.",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center
        )

        Text(
            text = "Sua coleção está esperando para ser preenchida com sabores incríveis. Comece a explorar e salve o que mais gostar!",
            modifier = Modifier.padding(top = spacing.md),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(spacing.xxl))
    }
}

@Preview(showBackground = true)
@Composable
private fun FavoritesEmptyStatePreview() {
    ChefIATheme {
        FavoritesEmptyState()
    }
}

@Preview(showBackground = true)
@Composable
private fun FavoritesListContentPreview() {
    ChefIATheme {
        FavoritesListContent(
            state = FavoritesUiState(
                recipes = listOf(
                    Recipe(
                        id = "1",
                        name = "Salmão Grelhado com Ervas",
                        description = "Um prato saudável e saboroso.",
                        preparationTimeMinutes = 40,
                        servings = 3,
                        caloriesPerServingKcal = 520,
                        difficulty = RecipeDifficulty.MEDIUM,
                        ingredients = emptyList(),
                        preparationSteps = emptyList()
                    )
                ),
                filteredRecipes = listOf(
                    Recipe(
                        id = "1",
                        name = "Salmão Grelhado com Ervas",
                        description = "Um prato saudável e saboroso.",
                        preparationTimeMinutes = 40,
                        servings = 3,
                        caloriesPerServingKcal = 520,
                        difficulty = RecipeDifficulty.MEDIUM,
                        ingredients = emptyList(),
                        preparationSteps = emptyList()
                    )
                )
            ),
            onAction = {}
        )
    }
}
