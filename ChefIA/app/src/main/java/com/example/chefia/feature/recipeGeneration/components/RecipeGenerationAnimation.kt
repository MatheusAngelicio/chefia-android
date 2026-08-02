package com.example.chefia.feature.recipeGeneration.components

import androidx.annotation.RawRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.chefia.R
import com.example.chefia.core.designsystem.theme.ChefIATheme

@Composable
fun RecipeLoadingAnimation(
    modifier: Modifier = Modifier,
    @RawRes animationRes: Int = R.raw.recipe_loading,
) {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(animationRes),
    )

    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever,
        restartOnPlay = false,
    )

    LottieAnimation(
        composition = composition,
        progress = { progress },
        modifier = modifier,
    )
}

@Preview(showBackground = true)
@Composable
private fun RecipeLoadingAnimationPreview() {
    ChefIATheme {
        RecipeLoadingAnimation()
    }
}