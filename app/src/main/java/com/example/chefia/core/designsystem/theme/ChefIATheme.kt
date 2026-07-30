package com.example.chefia.core.designsystem.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable

private val ColorScheme = lightColorScheme(
    primary = ChefIAColors.Primary,
    secondary = ChefIAColors.Secondary,
    background = ChefIAColors.Background,
    surface = ChefIAColors.Surface,
    error = ChefIAColors.Error
)

val MaterialTheme.spacing: ChefIASpacing
    @Composable
    @ReadOnlyComposable
    get() = LocalSpacing.current

@Composable
fun ChefIATheme(
    content: @Composable () -> Unit
) {

    CompositionLocalProvider(
        LocalSpacing provides ChefIASpacing()
    ) {
        MaterialTheme(
            colorScheme = ColorScheme,
            typography = ChefIATypography,
            content = content
        )
    }

}