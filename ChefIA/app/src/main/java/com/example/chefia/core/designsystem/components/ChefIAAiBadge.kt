package com.example.chefia.core.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AutoAwesome
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.chefia.core.designsystem.theme.ChefIAColors
import com.example.chefia.core.designsystem.theme.ChefIADimensions

@Composable
fun ChefIAAiBadge(
    modifier: Modifier = Modifier,
    iconModifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .background(
                color = ChefIAColors.AccentGreen,
                shape = CircleShape,
            ),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            imageVector = Icons.Rounded.AutoAwesome,
            contentDescription = null,
            modifier = iconModifier,
            tint = ChefIAColors.OnAccentGreen,
        )
    }
}

@Preview
@Composable
private fun ChefIAAiBadgePreview() {
    ChefIAAiBadge()
}