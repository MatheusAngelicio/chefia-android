package com.example.chefia.feature.camera

import android.graphics.Bitmap

data class CameraUiState(
    val capturedImage: Bitmap? = null,
    val isCapturing: Boolean = false,
    val isFlashEnabled: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null
) {
    val isReviewMode: Boolean get() = capturedImage != null
}