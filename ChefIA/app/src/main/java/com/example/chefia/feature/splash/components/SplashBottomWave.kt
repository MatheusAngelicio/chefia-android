package com.example.chefia.feature.splash.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.tooling.preview.Preview
import com.example.chefia.core.designsystem.theme.ChefIAColors
import com.example.chefia.core.designsystem.theme.ChefIADimensions
import com.example.chefia.core.designsystem.theme.ChefIATheme

@Composable
fun SplashBottomWave(
    modifier: Modifier = Modifier,
) {
    Canvas(
        modifier = modifier.fillMaxSize(),
    ) {
        val waveHeight =
            ChefIADimensions.SplashWaveHeight.toPx()

        val startY = size.height - waveHeight

        val wavePath = Path().apply {
            moveTo(
                x = 0f,
                y = startY,
            )

            cubicTo(
                x1 = size.width * 0.15f,
                y1 = startY + waveHeight * 0.65f,
                x2 = size.width * 0.28f,
                y2 = startY + waveHeight * 0.50f,
                x3 = size.width * 0.42f,
                y3 = startY + waveHeight * 0.28f,
            )

            cubicTo(
                x1 = size.width * 0.58f,
                y1 = startY - waveHeight * 0.05f,
                x2 = size.width * 0.62f,
                y2 = startY + waveHeight * 0.65f,
                x3 = size.width * 0.78f,
                y3 = startY + waveHeight * 0.28f,
            )

            cubicTo(
                x1 = size.width * 0.87f,
                y1 = startY + waveHeight * 0.05f,
                x2 = size.width * 0.94f,
                y2 = startY + waveHeight * 0.05f,
                x3 = size.width,
                y3 = startY + waveHeight * 0.14f,
            )

            lineTo(
                x = size.width,
                y = size.height,
            )

            lineTo(
                x = 0f,
                y = size.height,
            )

            close()
        }

        drawPath(
            path = wavePath,
            color = ChefIAColors.Wave,
            style = Fill,
        )
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
)
@Composable
private fun SplashBottomWavePreview() {
    ChefIATheme {
        SplashBottomWave()
    }
}