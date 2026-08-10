package com.example.chefia.feature.splash.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AutoAwesome
import androidx.compose.material.icons.rounded.Restaurant
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.chefia.R
import com.example.chefia.core.designsystem.components.ChefIAAiBadge
import com.example.chefia.core.designsystem.theme.ChefIAColors
import com.example.chefia.core.designsystem.theme.ChefIADimensions
import com.example.chefia.core.designsystem.theme.ChefIATheme

@Composable
fun SplashIllustration(
    modifier: Modifier = Modifier,
) {
    val infiniteTransition = rememberInfiniteTransition(
        label = "Splash Illustration",
    )

    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1400,
                easing = FastOutSlowInEasing,
            ),
            repeatMode = RepeatMode.Reverse,
        ),
        label = "Illustration Scale",
    )

    Box(
        modifier = modifier.size(
            ChefIADimensions.SplashIllustrationContainerSize,
        ),
        contentAlignment = Alignment.Center,
    ) {
        IllustrationBackground()

        Image(
            painter = painterResource(R.drawable.chefia_icon),
            contentDescription = "Ingredientes frescos do ChefIA",
            modifier = Modifier
                .size(ChefIADimensions.SplashIllustrationImageSize)
                .graphicsLayer {
                    scaleX = scale
                    scaleY = scale
                },
            contentScale = ContentScale.Fit,
        )

        RestaurantDecoration(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .offset(
                    x = 4.dp,
                    y = 12.dp,
                ),
        )

        ChefIAAiBadge(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .size(ChefIADimensions.SplashDecorationCircleSize)
                .offset(
                    x = 4.dp,
                    y = (-8).dp,
                ),
            iconModifier = Modifier.size(
                ChefIADimensions.SplashDecorationIconSize,
            ),
        )
    }
}

@Composable
private fun IllustrationBackground() {
    Box(
        modifier = Modifier
            .size(ChefIADimensions.SplashIllustrationBackgroundSize)
            .background(
                color = ChefIAColors.IllustrationBackground,
                shape = CircleShape,
            ),
    )
}

@Composable
private fun RestaurantDecoration(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .size(ChefIADimensions.SplashDecorationCircleSize)
            .background(
                color = ChefIAColors.Secondary.copy(alpha = 0.45f),
                shape = CircleShape,
            ),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            imageVector = Icons.Rounded.Restaurant,
            contentDescription = null,
            modifier = Modifier.size(
                ChefIADimensions.SplashDecorationIconSize,
            ),
            tint = ChefIAColors.OnSecondary,
        )
    }
}

@Composable
private fun AiDecoration(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .size(ChefIADimensions.SplashDecorationCircleSize)
            .background(
                color = ChefIAColors.AccentGreen,
                shape = CircleShape,
            ),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            imageVector = Icons.Rounded.AutoAwesome,
            contentDescription = null,
            modifier = Modifier.size(
                ChefIADimensions.SplashDecorationIconSize,
            ),
            tint = ChefIAColors.OnAccentGreen,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SplashIllustrationPreview() {
    ChefIATheme {
        SplashIllustration()
    }
}