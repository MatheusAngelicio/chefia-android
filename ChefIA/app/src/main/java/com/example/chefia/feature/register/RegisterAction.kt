package com.example.chefia.feature.register

sealed interface RegisterAction {
    data class NameChanged(val name: String) : RegisterAction
    data class EmailChanged(val email: String) : RegisterAction
    data class PasswordChanged(val password: String) : RegisterAction
    data class ConfirmPasswordChanged(val confirmPassword: String) : RegisterAction
    data object RegisterClicked : RegisterAction
    data object TogglePasswordVisibility : RegisterAction
    data object ToggleConfirmPasswordVisibility : RegisterAction
    data object BackToLoginClicked : RegisterAction
    data object DismissError : RegisterAction
}
