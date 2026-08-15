package com.example.chefia.feature.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chefia.core.common.util.AuthErrorMapper
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
            LoginAction.GoogleLoginClicked -> { /* Handle Google Login */ }
            LoginAction.ForgotPasswordClicked -> { /* Handle Forgot Password */ }
            LoginAction.RegisterClicked -> { /* Handle Register */ }
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
            _uiState.update { it.copy(errorMessage = "Preencha todos os campos") }
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
                        showErrorBottomSheet = true
                    )
                }
            }
        }
    }
}
