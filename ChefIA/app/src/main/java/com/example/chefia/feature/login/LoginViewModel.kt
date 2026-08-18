package com.example.chefia.feature.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chefia.core.common.util.AuthErrorMapper
import com.example.chefia.core.designsystem.components.ChefIAAlertBottomSheetType
import com.example.chefia.domain.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()

    fun onAction(action: LoginAction) {
        when (action) {
            is LoginAction.EmailChanged -> {
                _uiState.update { it.copy(email = action.email) }
            }
            is LoginAction.PasswordChanged -> {
                _uiState.update { it.copy(password = action.password) }
            }
            LoginAction.LoginClicked -> login()
            LoginAction.GoogleLoginClicked -> {
                _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            }
            is LoginAction.GoogleLoginSuccess -> loginWithGoogle(action.idToken)
            is LoginAction.GoogleLoginError -> {
                _uiState.update { 
                    it.copy(
                        isLoading = false, 
                        errorMessage = action.error,
                        showErrorBottomSheet = true,
                        alertType = ChefIAAlertBottomSheetType.ERROR
                    ) 
                }
            }
            LoginAction.ForgotPasswordClicked -> {
                _uiState.update { 
                    it.copy(
                        showForgotPasswordBottomSheet = true,
                        forgotPasswordEmail = it.email,
                        resetEmailSentSuccessfully = false
                    ) 
                }
            }
            is LoginAction.ForgotPasswordEmailChanged -> {
                _uiState.update { it.copy(forgotPasswordEmail = action.email) }
            }
            LoginAction.ForgotPasswordSubmit -> sendPasswordResetEmail()
            LoginAction.ForgotPasswordDismiss -> {
                _uiState.update { it.copy(showForgotPasswordBottomSheet = false) }
            }
            LoginAction.RegisterClicked -> { /* Handled in Screen */ }
            LoginAction.TogglePasswordVisibility -> {
                _uiState.update { it.copy(isPasswordVisible = !it.isPasswordVisible) }
            }
            LoginAction.DismissError -> {
                _uiState.update { it.copy(showErrorBottomSheet = false) }
            }
        }
    }

    private fun login() {
        val state = _uiState.value

        if (state.email.isBlank() || state.password.isBlank()) {
            _uiState.update { 
                it.copy(
                    errorMessage = "Preencha todos os campos",
                    showErrorBottomSheet = true,
                    alertType = ChefIAAlertBottomSheetType.WARNING
                ) 
            }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            
            authRepository.signIn(
                email = state.email,
                password = state.password
            ).onSuccess {
                _uiState.update { 
                    it.copy(
                        isLoading = false,
                        isLoginSuccessful = true 
                    )
                }
            }.onFailure { error ->
                _uiState.update { 
                    it.copy(
                        isLoading = false, 
                        errorMessage = AuthErrorMapper.map(error),
                        showErrorBottomSheet = true,
                        alertType = ChefIAAlertBottomSheetType.ERROR
                    )
                }
            }
        }
    }

    private fun loginWithGoogle(idToken: String) {
        viewModelScope.launch {
            authRepository.signInWithGoogle(idToken)
                .onSuccess {
                    _uiState.update { 
                        it.copy(
                            isLoading = false,
                            isLoginSuccessful = true 
                        )
                    }
                }
                .onFailure { error ->
                    _uiState.update { 
                        it.copy(
                            isLoading = false, 
                            errorMessage = AuthErrorMapper.map(error),
                            showErrorBottomSheet = true,
                            alertType = ChefIAAlertBottomSheetType.ERROR
                        )
                    }
                }
        }
    }

    private fun sendPasswordResetEmail() {
        val email = _uiState.value.forgotPasswordEmail
        if (email.isBlank()) {
            _uiState.update { 
                it.copy(
                    errorMessage = "Informe seu e-mail para recuperar a senha",
                    showErrorBottomSheet = true,
                    alertType = ChefIAAlertBottomSheetType.WARNING
                ) 
            }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isSendingResetEmail = true, errorMessage = null) }
            
            authRepository.sendPasswordResetEmail(email)
                .onSuccess {
                    _uiState.update { 
                        it.copy(
                            isSendingResetEmail = false,
                            resetEmailSentSuccessfully = true,
                            showForgotPasswordBottomSheet = false,
                            errorMessage = "E-mail de recuperação enviado com sucesso para $email",
                            showErrorBottomSheet = true,
                            alertType = ChefIAAlertBottomSheetType.SUCCESS
                        )
                    }
                }
                .onFailure { error ->
                    _uiState.update { 
                        it.copy(
                            isSendingResetEmail = false, 
                            errorMessage = AuthErrorMapper.map(error),
                            showErrorBottomSheet = true,
                            alertType = ChefIAAlertBottomSheetType.ERROR
                        )
                    }
                }
        }
    }
}
