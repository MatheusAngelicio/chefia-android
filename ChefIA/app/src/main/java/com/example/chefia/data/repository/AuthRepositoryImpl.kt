package com.example.chefia.data.repository

import com.example.chefia.domain.model.User
import com.example.chefia.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.userProfileChangeRequest
import kotlinx.coroutines.tasks.await

class AuthRepositoryImpl(
    private val firebaseAuth: FirebaseAuth
) : AuthRepository {

    override suspend fun signIn(email: String, password: String): Result<User> {
        return try {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            val firebaseUser = result.user ?: throw Exception("Falha ao fazer login")
            Result.success(
                User(
                    uid = firebaseUser.uid,
                    name = firebaseUser.displayName,
                    email = firebaseUser.email
                )
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun signInWithGoogle(idToken: String): Result<User> {
        return try {
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            val result = firebaseAuth.signInWithCredential(credential).await()
            val firebaseUser = result.user ?: throw Exception("Falha ao autenticar com Google")
            
            Result.success(
                User(
                    uid = firebaseUser.uid,
                    name = firebaseUser.displayName,
                    email = firebaseUser.email
                )
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun signUp(name: String, email: String, password: String): Result<User> {
        return try {
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            val firebaseUser = result.user ?: throw Exception("Falha ao criar usuário")
            
            val profileUpdates = userProfileChangeRequest {
                displayName = name
            }
            firebaseUser.updateProfile(profileUpdates).await()

            Result.success(
                User(
                    uid = firebaseUser.uid,
                    name = name,
                    email = firebaseUser.email
                )
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getCurrentUser(): User? {
        return firebaseAuth.currentUser?.let {
            User(
                uid = it.uid,
                name = it.displayName,
                email = it.email
            )
        }
    }

    override suspend fun signOut() {
        firebaseAuth.signOut()
    }
}
