package com.example.chefia.feature.camera

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chefia.domain.usecase.IdentifyIngredientsFromImageUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CameraViewModel(
    private val identifyIngredientsUseCase: IdentifyIngredientsFromImageUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(CameraUiState())
    val uiState = _uiState.asStateFlow()

    private val _ingredientsIdentified = MutableSharedFlow<Pair<List<String>, Bitmap>>()
    val ingredientsIdentified = _ingredientsIdentified.asSharedFlow()

    fun onAction(action: CameraAction) {
        when (action) {
            is CameraAction.PhotoCaptured -> {
                _uiState.update { it.copy(capturedImage = action.bitmap, isCapturing = false) }
            }
            CameraAction.RetakePhoto -> {
                _uiState.update { it.copy(capturedImage = null) }
            }
            CameraAction.ConfirmPhoto -> {
                confirmPhoto()
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

    private fun confirmPhoto() {
        val bitmap = uiState.value.capturedImage ?: return

        viewModelScope.launch {
            identifyIngredientsUseCase(bitmap)
                .onStart {
                    _uiState.update { it.copy(isLoading = true) }
                }
                .catch { exception ->
                    _uiState.update { it.copy(isLoading = false, error = exception.message) }
                }
                .collect { ingredients ->
                    _uiState.update { it.copy(isLoading = false) }
                    _ingredientsIdentified.emit(ingredients to bitmap)
                }
        }
    }

    fun onStartCapture() {
        _uiState.update { it.copy(isCapturing = true) }
    }
}
