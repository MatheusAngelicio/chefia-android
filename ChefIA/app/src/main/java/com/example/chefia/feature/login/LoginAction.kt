package com.example.chefia.feature.login

sealed interface LoginAction {
    data class EmailChanged(val email: String) : LoginAction
    data class PasswordChanged(val password: String) : LoginAction
    data object LoginClicked : LoginAction
    data object GoogleLoginClicked : LoginAction
    data class GoogleLoginSuccess(val idToken: String) : LoginAction
    data class GoogleLoginError(val error: String) : LoginAction
    data object ForgotPasswordClicked : LoginAction
    data object RegisterClicked : LoginAction
    data object TogglePasswordVisibility : LoginAction
    data object DismissError : LoginAction
    data class ForgotPasswordEmailChanged(val email: String) : LoginAction
    data object ForgotPasswordSubmit : LoginAction
    data object ForgotPasswordDismiss : LoginAction
}
