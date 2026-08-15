package com.example.chefia.feature.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import com.example.chefia.domain.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SplashViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SplashUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadInitialData()
    }

    private fun loadInitialData() {
        viewModelScope.launch {
            val user = authRepository.getCurrentUser()
            _uiState.update { it.copy(isAuthenticated = user != null) }
            
            simulateInitialLoading()
            completeLoading()
        }
    }

    private suspend fun simulateInitialLoading() {
        val totalSteps = 100
        val delayPerStep = 50L

        repeat(totalSteps) { index ->
            delay(delayPerStep)

            val currentProgress =
                (index + 1) / totalSteps.toFloat()

            _uiState.update { currentState ->
                currentState.copy(
                    progress = currentProgress,
                )
            }
        }
    }

    private fun completeLoading() {
        _uiState.update { currentState ->
            currentState.copy(
                progress = 1f,
                isLoadingComplete = true,
            )
        }
    }
}