package com.example.chefia.feature.register

import com.example.chefia.core.designsystem.components.ChefIAAlertBottomSheetType

data class RegisterUiState(
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val isPasswordVisible: Boolean = false,
    val isConfirmPasswordVisible: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val showErrorBottomSheet: Boolean = false,
    val isRegisterSuccessful: Boolean = false,
    val alertType: ChefIAAlertBottomSheetType = ChefIAAlertBottomSheetType.ERROR,
)
