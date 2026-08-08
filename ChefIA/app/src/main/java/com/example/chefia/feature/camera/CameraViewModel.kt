package com.example.chefia.feature.camera

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CameraViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(CameraUiState())
    val uiState = _uiState.asStateFlow()

    fun onAction(action: CameraAction) {
        when (action) {
            is CameraAction.PhotoCaptured -> {
                _uiState.update { it.copy(capturedImage = action.bitmap, isCapturing = false) }
            }
            CameraAction.RetakePhoto -> {
                _uiState.update { it.copy(capturedImage = null) }
            }
            CameraAction.ConfirmPhoto -> {
                // To be implemented: Send photo to processing
            }
            CameraAction.ToggleFlash -> {
                _uiState.update { it.copy(isFlashEnabled = !it.isFlashEnabled) }
            }
            is CameraAction.Error -> {
                _uiState.update { it.copy(error = action.message, isCapturing = false) }
            }
            CameraAction.DismissError -> {
                _uiState.update { it.copy(error = null) }
            }
        }
    }

    fun onStartCapture() {
        _uiState.update { it.copy(isCapturing = true) }
    }
}