package com.example.chefia.feature.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState = _uiState.asStateFlow()

    fun onAction(action: RegisterAction) {
        when (action) {
            is RegisterAction.NameChanged -> {
                _uiState.update { it.copy(name = action.name) }
            }
            is RegisterAction.EmailChanged -> {
                _uiState.update { it.copy(email = action.email) }
            }
            is RegisterAction.PasswordChanged -> {
                _uiState.update { it.copy(password = action.password) }
            }
            is RegisterAction.ConfirmPasswordChanged -> {
                _uiState.update { it.copy(confirmPassword = action.confirmPassword) }
            }
            RegisterAction.RegisterClicked -> register()
            RegisterAction.TogglePasswordVisibility -> {
                _uiState.update { it.copy(isPasswordVisible = !it.isPasswordVisible) }
            }
            RegisterAction.ToggleConfirmPasswordVisibility -> {
                _uiState.update { it.copy(isConfirmPasswordVisible = !it.isConfirmPasswordVisible) }
            }
            RegisterAction.BackToLoginClicked -> { /* Handled in Screen */ }
        }
    }

    private fun register() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            
            // Simulating API call
            delay(1500)
            
            _uiState.update { 
                it.copy(
                    isLoading = false,
                    isRegisterSuccessful = true 
                )
            }
        }
    }
}
