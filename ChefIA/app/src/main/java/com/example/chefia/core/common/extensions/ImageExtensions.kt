package com.example.chefia.core.common.extensions

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import androidx.camera.core.ImageProxy
import java.io.File
import java.io.FileOutputStream

fun ImageProxy.toBitmap(): Bitmap {
    val buffer = planes[0].buffer
    val bytes = ByteArray(buffer.remaining())
    buffer.get(bytes)
    return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
}

fun Bitmap.rotate(degrees: Int): Bitmap {
    val matrix = Matrix()
    matrix.postRotate(degrees.toFloat())
    return Bitmap.createBitmap(this, 0, 0, width, height, matrix, true)
}

fun Bitmap.saveToTempFile(context: Context): String {
    val file = File(context.cacheDir, "temp_photo_${System.currentTimeMillis()}.jpg")
    FileOutputStream(file).use { out ->
        this.compress(Bitmap.CompressFormat.JPEG, 90, out)
    }
    return file.absolutePath
}

fun deleteTempPhoto(path: String) {
    val file = File(path)
    if (file.exists()) {
        file.delete()
    }
}
