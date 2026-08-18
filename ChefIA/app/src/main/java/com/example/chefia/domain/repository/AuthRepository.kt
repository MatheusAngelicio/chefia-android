package com.example.chefia.domain.repository

import com.example.chefia.domain.model.User

interface AuthRepository {
    suspend fun signIn(email: String, password: String): Result<User>
    suspend fun signInWithGoogle(idToken: String): Result<User>
    suspend fun signUp(name: String, email: String, password: String): Result<User>
    suspend fun getCurrentUser(): User?
    suspend fun signOut()
    suspend fun sendPasswordResetEmail(email: String): Result<Unit>
}
