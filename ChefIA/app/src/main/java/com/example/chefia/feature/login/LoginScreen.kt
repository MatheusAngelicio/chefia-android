package com.example.chefia.feature.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material.icons.rounded.VisibilityOff
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.chefia.R
import com.example.chefia.core.designsystem.components.AuthTopDecoration
import com.example.chefia.core.designsystem.components.ChefIAAlertBottomSheet
import com.example.chefia.core.designsystem.components.ChefIAButton
import com.example.chefia.core.designsystem.components.ChefIATextField
import com.example.chefia.core.designsystem.theme.ChefIAColors
import com.example.chefia.core.designsystem.theme.ChefIADimensions
import com.example.chefia.core.designsystem.theme.ChefIATheme
import com.example.chefia.core.designsystem.theme.spacing
import com.example.chefia.feature.login.util.GoogleAuthUiClient
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onNavigateToRegister: () -> Unit,
    viewModel: LoginViewModel = koinViewModel(),
    googleAuthUiClient: GoogleAuthUiClient = koinInject(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(state.isLoginSuccessful) {
        if (state.isLoginSuccessful) {
            onLoginSuccess()
        }
    }

    Scaffold { innerPadding ->
        LoginContent(
            state = state,
            onAction = { action ->
                when (action) {
                    is LoginAction.RegisterClicked -> onNavigateToRegister()
                    LoginAction.GoogleLoginClicked -> {
                        viewModel.onAction(action)
                        coroutineScope.launch {
                            try {
                                val idToken = googleAuthUiClient.signIn()
                                if (idToken != null) {
                                    viewModel.onAction(LoginAction.GoogleLoginSuccess(idToken))
                                } else {
                                    viewModel.onAction(LoginAction.GoogleLoginError("Falha ao obter token do Google"))
                                }
                            } catch (e: Exception) {
                                viewModel.onAction(LoginAction.GoogleLoginError(e.localizedMessage ?: "Erro ao autenticar com Google"))
                            }
                        }
                    }
                    else -> viewModel.onAction(action)
                }
            },
            modifier = Modifier.padding(innerPadding)
        )

        if (state.showErrorBottomSheet && state.errorMessage != null) {
            ChefIAAlertBottomSheet(
                title = "Atenção",
                description = state.errorMessage!!,
                onDismiss = { viewModel.onAction(LoginAction.DismissError) }
            )
        }
    }
}

@Composable
private fun LoginContent(
    state: LoginUiState,
    onAction: (LoginAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    val spacing = MaterialTheme.spacing

    BoxWithConstraints(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .imePadding()
    ) {
        val minHeight = maxHeight

        AuthTopDecoration(
            modifier = Modifier.align(Alignment.TopCenter)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = minHeight)
                    .padding(horizontal = spacing.xl),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Spacer(modifier = Modifier.height(60.dp))

                // Logo with stylized background
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .background(ChefIAColors.IllustrationBackground),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.chefia_icon),
                        contentDescription = null,
                        modifier = Modifier.size(70.dp),
                        contentScale = ContentScale.Fit
                    )
                }

                Spacer(modifier = Modifier.height(spacing.xl))

                Text(
                    text = "Bem-vindo de volta!",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center
                )

                Text(
                    text = "Entre para descobrir novas receitas",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(spacing.xxl))

                // Form Card
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(ChefIADimensions.CardCornerRadius),
                    color = MaterialTheme.colorScheme.surface,
                    shadowElevation = 2.dp
                ) {
                    Column(
                        modifier = Modifier.padding(spacing.lg),
                        verticalArrangement = Arrangement.spacedBy(spacing.md)
                    ) {
                        Text(
                            text = "Email",
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.onBackground
                        )

                        ChefIATextField(
                            value = state.email,
                            onValueChange = { onAction(LoginAction.EmailChanged(it)) },
                            placeholder = "seu@email.com",
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Rounded.Email,
                                    contentDescription = null,
                                    tint = ChefIAColors.Primary
                                )
                            },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Senha",
                                style = MaterialTheme.typography.labelLarge,
                                color = MaterialTheme.colorScheme.onBackground
                            )

                            Text(
                                text = "Esqueceu a senha?",
                                style = MaterialTheme.typography.labelMedium,
                                color = ChefIAColors.PrimaryContainer,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier.clickable { onAction(LoginAction.ForgotPasswordClicked) }
                            )
                        }

                        ChefIATextField(
                            value = state.password,
                            onValueChange = { onAction(LoginAction.PasswordChanged(it)) },
                            placeholder = "••••••••",
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Rounded.Lock,
                                    contentDescription = null,
                                    tint = ChefIAColors.Primary
                                )
                            },
                            trailingIcon = {
                                IconButton(onClick = { onAction(LoginAction.TogglePasswordVisibility) }) {
                                    Icon(
                                        imageVector = if (state.isPasswordVisible) Icons.Rounded.VisibilityOff else Icons.Rounded.Visibility,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            },
                            visualTransformation = if (state.isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                        )

                        Spacer(modifier = Modifier.height(spacing.sm))

                    ChefIAButton(
                        text = "Entrar",
                        onClick = { onAction(LoginAction.LoginClicked) },
                        enabled = !state.isLoading,
                        isLoading = state.isLoading
                    )
                    }
                }

                Spacer(modifier = Modifier.height(spacing.xl))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(spacing.sm)
                ) {
                    HorizontalDivider(modifier = Modifier.weight(1f), color = ChefIAColors.Wave)
                    Text(
                        text = "Ou entre com",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    HorizontalDivider(modifier = Modifier.weight(1f), color = ChefIAColors.Wave)
                }

                Spacer(modifier = Modifier.height(spacing.xl))

                OutlinedButton(
                    onClick = { onAction(LoginAction.GoogleLoginClicked) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    enabled = !state.isLoading,
                    shape = RoundedCornerShape(26.dp), // Pill shaped
                    border = androidx.compose.foundation.BorderStroke(1.dp, ChefIAColors.Border)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(spacing.sm)
                    ) {
                        // Simple G text as icon fallback
                        Text(
                            text = "G",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF4285F4)
                        )
                        Text(
                            text = "Continuar com Google",
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = buildAnnotatedString {
                        append("Não tem uma conta? ")
                        withStyle(
                            SpanStyle(
                                color = ChefIAColors.PrimaryContainer,
                                fontWeight = FontWeight.Bold
                            )
                        ) {
                            append("Cadastre-se")
                        }
                    },
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier
                        .padding(vertical = spacing.xl)
                        .clickable { onAction(LoginAction.RegisterClicked) }
                )
            }
        }

        if (state.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.1f))
                    .clickable(enabled = false) {},
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.primary,
                    strokeCap = StrokeCap.Round
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun LoginContentPreview() {
    ChefIATheme {
        LoginContent(
            state = LoginUiState(),
            onAction = {}
        )
    }
}
