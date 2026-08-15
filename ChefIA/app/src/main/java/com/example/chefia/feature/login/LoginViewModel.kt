package com.example.chefia.feature.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

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
        }
    }

    private fun login() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            
            // Simulating API call
            delay(1500)

            _uiState.update { 
                it.copy(
                    isLoading = false,
                    isLoginSuccessful = true
                )
            }
        }
    }
}
