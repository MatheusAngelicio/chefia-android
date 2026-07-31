package com.example.chefia.feature.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.chefia.core.designsystem.components.ChefIAProgressIndicator
import com.example.chefia.core.designsystem.theme.ChefIADimensions
import com.example.chefia.core.designsystem.theme.ChefIATheme
import com.example.chefia.core.designsystem.theme.spacing
import com.example.chefia.feature.splash.components.SplashBottomWave
import com.example.chefia.feature.splash.components.SplashIllustration

@Composable
fun SplashScreen(
    onLoadingComplete: () -> Unit,
    viewModel: SplashViewModel = viewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(state.isLoadingComplete) {
        if (state.isLoadingComplete) {
            onLoadingComplete()
        }
    }

    SplashContent(
        state = state,
    )
}

@Composable
private fun SplashContent(
    state: SplashUiState,
) {
    val spacing = MaterialTheme.spacing

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
    ) {
        SplashBottomWave()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = spacing.xl),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(
                modifier = Modifier.weight(0.16f),
            )

            SplashIllustration()

            Spacer(
                modifier = Modifier.height(spacing.xl),
            )

            Text(
                text = "ChefIA",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
            )

            Text(
                text = "A inteligência artificial que transforma seus ingredientes em alta gastronomia.",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = spacing.sm,
                        start = spacing.md,
                        end = spacing.md,
                    ),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
            )

            Spacer(
                modifier = Modifier.height(spacing.xxl),
            )

            ChefIAProgressIndicator(
                progress = state.progress,
                modifier = Modifier.fillMaxWidth(
                    ChefIADimensions.SplashProgressWidthFraction,
                ),
            )

            Text(
                text = "CARREGANDO SUA COZINHA...",
                modifier = Modifier.padding(top = spacing.md),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
            )

            Spacer(
                modifier = Modifier.weight(0.20f),
            )
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
)
@Composable
private fun SplashContentPreview() {
    ChefIATheme {
        SplashContent(
            state = SplashUiState(
                progress = 0.65f,
            ),
        )
    }
}