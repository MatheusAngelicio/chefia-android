package com.example.chefia.feature.login

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val isPasswordVisible: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val showErrorBottomSheet: Boolean = false,
    val isLoginSuccessful: Boolean = false,
)
