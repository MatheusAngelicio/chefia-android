package com.example.chefia.feature.recipeExecution.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chefia.core.designsystem.theme.spacing
import com.example.chefia.domain.model.RecipeStep

@Composable
fun RecipeExecutionStepCard(
    step: RecipeStep,
    stepNumber: Int,
    modifier: Modifier = Modifier,
) {
    val spacing = MaterialTheme.spacing

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(Color(0xFFF7F7F7)) // Light Gray from Figma
            .padding(spacing.lg)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(spacing.md),
            verticalAlignment = Alignment.Top
        ) {
            // Circle Number
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF1B5E20)), // Dark Green
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stepNumber.toString(),
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                )
            }

            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = step.title.uppercase(),
                    style = MaterialTheme.typography.labelLarge.copy(
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = 1.sp,
                        color = Color(0xFF1B5E20)
                    )
                )
                Text(
                    text = step.description,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        lineHeight = 24.sp
                    ),
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}
