package com.example.chefia.feature.camera

import android.graphics.Bitmap

sealed interface CameraAction {
    data class PhotoCaptured(val bitmap: Bitmap) : CameraAction
    data object RetakePhoto : CameraAction
    data object ConfirmPhoto : CameraAction
    data object ToggleFlash : CameraAction
    data class Error(val message: String) : CameraAction
    data object DismissError : CameraAction
}