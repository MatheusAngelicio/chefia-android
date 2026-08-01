package com.example.chefia.core.designsystem.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.chefia.core.designsystem.theme.ChefIAColors
import com.example.chefia.core.designsystem.theme.ChefIADimensions
import com.example.chefia.core.designsystem.theme.ChefIATheme

@Composable
fun ChefIAProgressIndicator(
    progress: Float,
    modifier: Modifier = Modifier,
) {
    LinearProgressIndicator(
        progress = {
            progress.coerceIn(
                minimumValue = 0f,
                maximumValue = 1f,
            )
        },
        modifier = modifier
            .fillMaxWidth()
            .height(ChefIADimensions.SplashProgressHeight),
        color = ChefIAColors.Primary,
        trackColor = ChefIAColors.ProgressTrack,
        strokeCap = StrokeCap.Round,
        gapSize = 0.dp,
        drawStopIndicator = {}
    )
}

@Preview(showBackground = true)
@Composable
private fun ChefIAProgressIndicatorPreview() {
    ChefIATheme {
        ChefIAProgressIndicator(
            progress = 0.65f,
        )
    }
}