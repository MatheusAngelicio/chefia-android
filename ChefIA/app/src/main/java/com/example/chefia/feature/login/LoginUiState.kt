package com.example.chefia.feature.login

import com.example.chefia.core.designsystem.components.ChefIAAlertBottomSheetType

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val isPasswordVisible: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val showErrorBottomSheet: Boolean = false,
    val isLoginSuccessful: Boolean = false,
    val showForgotPasswordBottomSheet: Boolean = false,
    val forgotPasswordEmail: String = "",
    val isSendingResetEmail: Boolean = false,
    val resetEmailSentSuccessfully: Boolean = false,
    val alertType: ChefIAAlertBottomSheetType = ChefIAAlertBottomSheetType.ERROR,
)
