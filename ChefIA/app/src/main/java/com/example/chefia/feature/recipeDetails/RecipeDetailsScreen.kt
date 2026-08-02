package com.example.chefia.feature.recipeDetails

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.chefia.core.designsystem.components.ChefIATopBar
import com.example.chefia.core.designsystem.theme.spacing
import com.example.chefia.domain.model.Recipe

@Composable
fun RecipeDetailsScreen(
    recipe: Recipe,
    onBackClick: () -> Unit,
) {
    Scaffold(
        topBar = {
            ChefIATopBar(
                onBackClick = onBackClick,
                actions = {
                    IconButton(
                        onClick = {
                            // TODO: Implement revenue sharing via WhatsApp or email.
                        },
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Share,
                            contentDescription = "Compartilhar receita",
                        )
                    }
                },
            )
        },
    ) { innerPadding ->
        RecipeDetailsContent(
            recipe = recipe,
            modifier = Modifier.padding(innerPadding),
        )
    }
}

@Composable
private fun RecipeDetailsContent(
    recipe: Recipe,
    modifier: Modifier = Modifier,
) {
    val spacing = MaterialTheme.spacing

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(
            bottom = spacing.xl,
        ),
    ) {
//        item {
//            RecipeDetailsHeader(
//                recipe = recipe,
//            )
//        }
    }
}