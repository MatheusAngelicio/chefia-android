package com.example.chefia.feature.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CameraAlt
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.chefia.core.designsystem.theme.ChefIATheme
import com.example.chefia.core.designsystem.theme.spacing

enum class HomeActionCardOrientation {
    Vertical,
    Horizontal,
}

@Composable
fun HomeActionCard(
    title: String,
    icon: ImageVector,
    backgroundColor: Color,
    contentColor: Color,
    iconBackgroundColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    orientation: HomeActionCardOrientation = HomeActionCardOrientation.Vertical,
) {
    val spacing = MaterialTheme.spacing

    val cardModifier = modifier
        .fillMaxWidth()
        .background(
            color = backgroundColor,
            shape = RoundedCornerShape(36.dp),
        )
        .clickable(
            role = Role.Button,
            onClick = onClick,
        )
        .padding(spacing.lg)

    when (orientation) {
        HomeActionCardOrientation.Vertical -> {
            Column(
                modifier = cardModifier.height(214.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                ActionIcon(
                    icon = icon,
                    contentColor = contentColor,
                    backgroundColor = iconBackgroundColor,
                )

                Spacer(modifier = Modifier.height(spacing.lg))

                Text(
                    text = title,
                    color = contentColor,
                    style = MaterialTheme.typography.titleLarge,
                )
            }
        }

        HomeActionCardOrientation.Horizontal -> {
            Row(
                modifier = cardModifier.height(110.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(spacing.md),
            ) {
                ActionIcon(
                    icon = icon,
                    contentColor = contentColor,
                    backgroundColor = iconBackgroundColor,
                )

                Text(
                    text = title,
                    color = contentColor,
                    style = MaterialTheme.typography.titleLarge,
                )
            }
        }
    }
}

@Composable
private fun ActionIcon(
    icon: ImageVector,
    contentColor: Color,
    backgroundColor: Color,
) {
    Box(
        modifier = Modifier
            .size(80.dp)
            .background(
                color = backgroundColor,
                shape = CircleShape,
            ),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(36.dp),
            tint = contentColor,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeActionCardPreview() {
    ChefIATheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            HomeActionCard(
                title = "Tirar foto da geladeira",
                icon = Icons.Rounded.CameraAlt,
                backgroundColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                iconBackgroundColor = MaterialTheme.colorScheme.primaryContainer,
                onClick = {},
            )

            HomeActionCard(
                title = "Digitar ingredientes",
                icon = Icons.Rounded.Edit,
                backgroundColor = MaterialTheme.colorScheme.secondary,
                contentColor = MaterialTheme.colorScheme.onSecondary,
                iconBackgroundColor =
                    MaterialTheme.colorScheme.secondaryContainer,
                orientation = HomeActionCardOrientation.Horizontal,
                onClick = {},
            )
        }
    }
}