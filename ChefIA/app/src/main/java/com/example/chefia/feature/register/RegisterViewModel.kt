package com.example.chefia.feature.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chefia.core.common.util.AuthErrorMapper
import com.example.chefia.domain.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {

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
            RegisterAction.DismissError -> {
                _uiState.update { it.copy(showErrorBottomSheet = false) }
            }
        }
    }

    private fun register() {
        val state = _uiState.value
        
        if (state.password != state.confirmPassword) {
            _uiState.update { 
                it.copy(
                    errorMessage = "As senhas não coincidem",
                    showErrorBottomSheet = true
                ) 
            }
            return
        }

        if (state.name.isBlank() || state.email.isBlank() || state.password.isBlank()) {
            _uiState.update { 
                it.copy(
                    errorMessage = "Preencha todos os campos",
                    showErrorBottomSheet = true
                ) 
            }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            
            authRepository.signUp(
                name = state.name,
                email = state.email,
                password = state.password
            ).onSuccess {
                _uiState.update { 
                    it.copy(
                        isLoading = false,
                        isRegisterSuccessful = true 
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
