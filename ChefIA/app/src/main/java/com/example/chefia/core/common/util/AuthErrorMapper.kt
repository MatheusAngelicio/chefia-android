package com.example.chefia.core.common.util

import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException

object AuthErrorMapper {
    fun map(throwable: Throwable): String {
        return when (throwable) {
            is FirebaseAuthWeakPasswordException -> "A senha fornecida é muito fraca. Use pelo menos 6 caracteres com letras e números."
            is FirebaseAuthUserCollisionException -> "Este e-mail já está sendo usado por outra conta."
            is FirebaseAuthInvalidUserException -> "Usuário não encontrado. Verifique se o e-mail está correto."
            is FirebaseAuthInvalidCredentialsException -> "Dados de acesso incorretos. Por favor, verifique seu e-mail e senha."
            is FirebaseNetworkException -> "Erro de conexão. Verifique se você está conectado à internet."
            is FirebaseException -> {
                if (throwable.message?.contains("App attestation failed", ignoreCase = true) == true) {
                    "Falha de segurança (App Check). Se estiver desenvolvendo, registre seu token de debug no console do Firebase."
                } else {
                    "Ocorreu um erro no serviço do Firebase. Tente novamente em instantes."
                }
            }
            else -> "Ocorreu um erro inesperado (${throwable.localizedMessage}). Tente novamente mais tarde."
        }
    }
}
