package com.example.chefia.feature.login.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.example.chefia.core.designsystem.components.ChefIAButton
import com.example.chefia.core.designsystem.components.ChefIABottomSheet
import com.example.chefia.core.designsystem.components.ChefIATextField
import com.example.chefia.core.designsystem.theme.ChefIAColors
import com.example.chefia.core.designsystem.theme.ChefIATheme
import com.example.chefia.core.designsystem.theme.spacing

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForgotPasswordBottomSheet(
    email: String,
    onEmailChanged: (String) -> Unit,
    onSubmit: () -> Unit,
    onDismiss: () -> Unit,
    isLoading: Boolean = false,
) {
    val spacing = MaterialTheme.spacing

    ChefIABottomSheet(
        onDismissRequest = onDismiss,
        title = "Recuperar Senha",
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = spacing.xl, vertical = spacing.lg),
            verticalArrangement = Arrangement.spacedBy(spacing.md)
        ) {
            Text(
                text = "Informe seu e-mail cadastrado para receber as instruções de redefinição de senha.",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(spacing.sm))

            Text(
                text = "E-mail",
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            ChefIATextField(
                value = email,
                onValueChange = onEmailChanged,
                placeholder = "seu@email.com",
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Rounded.Email,
                        contentDescription = null,
                        tint = ChefIAColors.Primary
                    )
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                enabled = !isLoading
            )

            Spacer(modifier = Modifier.height(spacing.md))

            ChefIAButton(
                text = "Enviar Link",
                onClick = onSubmit,
                isLoading = isLoading,
                enabled = !isLoading && email.isNotBlank()
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ForgotPasswordBottomSheetPreview() {
    ChefIATheme {
        ForgotPasswordBottomSheet(
            email = "usuario@exemplo.com",
            onEmailChanged = {},
            onSubmit = {},
            onDismiss = {}
        )
    }
}
