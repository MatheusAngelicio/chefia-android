package com.example.chefia.core.designsystem.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.chefia.core.designsystem.theme.ChefIAColors
import com.example.chefia.core.designsystem.theme.ChefIATheme

@Composable
fun AuthTopDecoration(
    modifier: Modifier = Modifier,
) {
    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(180.dp),
    ) {
        val wavePath = Path().apply {
            moveTo(0f, 0f)
            lineTo(size.width, 0f)
            lineTo(size.width, size.height * 0.5f)
            
            cubicTo(
                x1 = size.width * 0.8f,
                y1 = size.height * 0.8f,
                x2 = size.width * 0.5f,
                y2 = size.height * 0.2f,
                x3 = 0f,
                y3 = size.height * 0.9f,
            )
            close()
        }

        drawPath(
            path = wavePath,
            color = ChefIAColors.Wave.copy(alpha = 0.6f),
            style = Fill,
        )

        val circlePath = Path().apply {
            addOval(
                androidx.compose.ui.geometry.Rect(
                    left = -50.dp.toPx(),
                    top = -50.dp.toPx(),
                    right = 120.dp.toPx(),
                    bottom = 120.dp.toPx()
                )
            )
        }

        drawPath(
            path = circlePath,
            color = ChefIAColors.AccentGreen.copy(alpha = 0.2f),
            style = Fill
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun AuthTopDecorationPreview() {
    ChefIATheme {
        AuthTopDecoration()
    }
}
