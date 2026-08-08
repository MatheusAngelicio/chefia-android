package com.example.chefia.feature.camera.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun CameraFramingOverlay(
    modifier: Modifier = Modifier,
    verticalOffset: Dp = 0.dp
) {

    Canvas(modifier = modifier.fillMaxSize()) {
        val width = size.width
        val height = size.height
        val padding = 40.dp.toPx()
        val cornerSize = 40.dp.toPx()
        val strokeWidth = 4.dp.toPx()
        val cornerColor = Color(0xFF4CAF50)

        // Dimensões da área de enquadramento
        val frameHeight = height * 0.6f // 60% da altura da tela
        val top = ((height - frameHeight) / 2) + verticalOffset.toPx()
        val left = padding
        val right = width - padding
        val bottom = top + frameHeight

        // 1. Escurecer o fundo (opcional se quiser focar apenas no frame)
        // drawRect(color = Color.Black.copy(alpha = 0.3f))

        // 2. Desenhar cantos
        // Top Left
        drawLine(
            color = cornerColor,
            start = Offset(left, top + cornerSize),
            end = Offset(left, top),
            strokeWidth = strokeWidth
        )
        drawLine(
            color = cornerColor,
            start = Offset(left, top),
            end = Offset(left + cornerSize, top),
            strokeWidth = strokeWidth
        )

        // Top Right
        drawLine(
            color = cornerColor,
            start = Offset(right - cornerSize, top),
            end = Offset(right, top),
            strokeWidth = strokeWidth
        )
        drawLine(
            color = cornerColor,
            start = Offset(right, top),
            end = Offset(right, top + cornerSize),
            strokeWidth = strokeWidth
        )

        // Bottom Left
        drawLine(
            color = cornerColor,
            start = Offset(left, bottom - cornerSize),
            end = Offset(left, bottom),
            strokeWidth = strokeWidth
        )
        drawLine(
            color = cornerColor,
            start = Offset(left, bottom),
            end = Offset(left + cornerSize, bottom),
            strokeWidth = strokeWidth
        )

        // Bottom Right
        drawLine(
            color = cornerColor,
            start = Offset(right - cornerSize, bottom),
            end = Offset(right, bottom),
            strokeWidth = strokeWidth
        )
        drawLine(
            color = cornerColor,
            start = Offset(right, bottom),
            end = Offset(right, bottom - cornerSize),
            strokeWidth = strokeWidth
        )
    }
}