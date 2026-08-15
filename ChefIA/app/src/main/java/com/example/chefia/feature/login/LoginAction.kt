package com.example.chefia.feature.login

sealed interface LoginAction {
    data class EmailChanged(val email: String) : LoginAction
    data class PasswordChanged(val password: String) : LoginAction
    data object LoginClicked : LoginAction
    data object GoogleLoginClicked : LoginAction
    data object ForgotPasswordClicked : LoginAction
    data object RegisterClicked : LoginAction
    data object TogglePasswordVisibility : LoginAction
}
