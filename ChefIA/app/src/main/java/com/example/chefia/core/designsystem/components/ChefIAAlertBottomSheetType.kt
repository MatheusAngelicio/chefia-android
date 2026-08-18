package com.example.chefia.core.designsystem.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.Error
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

enum class ChefIAAlertBottomSheetType {
    SUCCESS,
    ERROR,
    WARNING,
    INFO;

    val icon: ImageVector
        get() = when (this) {
            SUCCESS -> Icons.Rounded.CheckCircle
            ERROR -> Icons.Rounded.Error
            WARNING -> Icons.Rounded.Warning
            INFO -> Icons.Rounded.Info
        }

    @Composable
    fun getColor(): Color = when (this) {
        SUCCESS -> Color(0xFF4CAF50)
        ERROR -> Color(0xFFF44336)
        WARNING -> Color(0xFFFF9800)
        INFO -> Color(0xFF2196F3)
    }
}
