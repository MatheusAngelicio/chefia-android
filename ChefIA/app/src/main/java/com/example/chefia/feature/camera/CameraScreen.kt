package com.example.chefia.feature.camera

import android.Manifest
import android.graphics.Bitmap
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageProxy
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.FlashOff
import androidx.compose.material.icons.rounded.FlashOn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.graphics.createBitmap
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.chefia.core.common.extensions.rotate
import com.example.chefia.core.common.extensions.saveToTempFile
import com.example.chefia.core.common.extensions.toBitmap
import com.example.chefia.core.designsystem.components.ChefIAButton
import com.example.chefia.core.designsystem.theme.ChefIAColors
import com.example.chefia.core.designsystem.theme.ChefIATheme
import com.example.chefia.core.designsystem.theme.spacing
import com.example.chefia.feature.camera.components.CameraFramingOverlay
import com.example.chefia.feature.camera.components.CameraPreviewView
import org.koin.androidx.compose.koinViewModel

@Composable
fun CameraScreen(
    onBack: () -> Unit,
    onNavigateToIngredientsConfirmation: (ingredients: List<String>, photoPath: String) -> Unit,
    viewModel: CameraViewModel = koinViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    var hasCameraPermission by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.ingredientsIdentified.collect { (ingredients, bitmap) ->
            val photoPath = bitmap.saveToTempFile(context)
            onNavigateToIngredientsConfirmation(ingredients, photoPath)
        }
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { granted ->
            hasCameraPermission = granted
            if (!granted) onBack()
        }
    )

    LaunchedEffect(Unit) {
        permissionLauncher.launch(Manifest.permission.CAMERA)
    }

    CameraContent(
        state = state,
        hasCameraPermission = hasCameraPermission,
        onBack = onBack,
        onAction = { action ->
            viewModel.onAction(action)
        },
        onTakePicture = { imageCapture ->
            viewModel.onStartCapture()
            
            // Configurar flash
            imageCapture.flashMode = if (state.isFlashEnabled) ImageCapture.FLASH_MODE_ON else ImageCapture.FLASH_MODE_OFF

            imageCapture.takePicture(
                ContextCompat.getMainExecutor(context),
                object : ImageCapture.OnImageCapturedCallback() {
                    override fun onCaptureSuccess(image: ImageProxy) {
                        val bitmap = image.toBitmap().rotate(image.imageInfo.rotationDegrees)
                        viewModel.onAction(CameraAction.PhotoCaptured(bitmap))
                        image.close()
                    }

                    override fun onError(exception: androidx.camera.core.ImageCaptureException) {
                        viewModel.onAction(CameraAction.Error(exception.message ?: "Erro ao capturar foto"))
                    }
                }
            )
        }
    )
}

@Composable
private fun  CameraContent(
    state: CameraUiState,
    hasCameraPermission: Boolean,
    onBack: () -> Unit,
    onAction: (CameraAction) -> Unit,
    onTakePicture: (ImageCapture) -> Unit,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = ChefIAColors.Black
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            if (hasCameraPermission) {
                if (state.isReviewMode) {
                    CameraReviewContent(
                        bitmap = state.capturedImage!!,
                        onConfirm = { onAction(CameraAction.ConfirmPhoto) },
                        onRetake = { onAction(CameraAction.RetakePhoto) }
                    )
                } else {
                    CameraCaptureContent(
                        isCapturing = state.isCapturing,
                        isFlashEnabled = state.isFlashEnabled,
                        onCapture = onTakePicture,
                        onToggleFlash = { onAction(CameraAction.ToggleFlash) },
                        onBack = onBack
                    )
                }
            }

            if (state.isCapturing) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(ChefIAColors.Black.copy(alpha = 0.5f)),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = ChefIAColors.White)
                }
            }

            if (state.isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(ChefIAColors.Black.copy(alpha = 0.7f)),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        CircularProgressIndicator(color = ChefIAColors.White)
                        Text(
                            text = "Identificando ingredientes...",
                            color = ChefIAColors.White,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun CameraCaptureContent(
    isCapturing: Boolean,
    isFlashEnabled: Boolean,
    onCapture: (ImageCapture) -> Unit,
    onToggleFlash: () -> Unit,
    onBack: () -> Unit,
) {
    val imageCapture = remember { ImageCapture.Builder().build() }

    Box(modifier = Modifier.fillMaxSize()) {
        CameraPreviewView(
            imageCapture = imageCapture,
            modifier = Modifier.fillMaxSize()
        )

        CameraFramingOverlay(
            modifier = Modifier.fillMaxSize(),
            verticalOffset = 40.dp
        )

        // Top Controls
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onBack,
                modifier = Modifier.size(48.dp)
            ) {
                Icon(
                    imageVector = Icons.Rounded.Close,
                    contentDescription = "Fechar",
                    tint = ChefIAColors.White,
                    modifier = Modifier.size(28.dp)
                )
            }

            IconButton(
                onClick = onToggleFlash,
                modifier = Modifier.size(48.dp)
            ) {
                Icon(
                    imageVector = if (isFlashEnabled) Icons.Rounded.FlashOn else Icons.Rounded.FlashOff,
                    contentDescription = "Flash",
                    tint = ChefIAColors.White,
                    modifier = Modifier.size(28.dp)
                )
            }
        }

        // Instructions Banner
        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 76.dp)
                .background(
                    color = ChefIAColors.Black .copy(alpha = 0.6f),
                    shape = RoundedCornerShape(32.dp)
                )
                .border(
                    width = 1.dp,
                    color = ChefIAColors.White.copy(alpha = 0.2f),
                    shape = RoundedCornerShape(32.dp)
                )
                .padding(horizontal = 24.dp, vertical = 12.dp)
        ) {
            Text(
                text = "Aponte para os ingredientes e\ntire uma foto",
                color = ChefIAColors.White,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center,
                lineHeight = 22.sp
            )
        }

        // Bottom Controls
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 60.dp)
        ) {
            // Outer Ring
            Box(
                modifier = Modifier
                    .size(86.dp)
                    .border(4.dp, ChefIAColors.White.copy(alpha = 0.3f), CircleShape)
            )

            // Inner Button
            IconButton(
                onClick = { if (!isCapturing) onCapture(imageCapture) },
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(70.dp)
                    .background(ChefIAColors.White, CircleShape)
            ) {
                // Empty - Just the white circle
            }
        }
    }
}

@Composable
private fun CameraReviewContent(
    bitmap: Bitmap,
    onConfirm: () -> Unit,
    onRetake: () -> Unit,
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier.weight(1f)) {
            Image(
                bitmap = bitmap.asImageBitmap(),
                contentDescription = "Preview da foto",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 32.dp)
                    .background(
                        color = ChefIAColors.Black.copy(alpha = 0.7f),
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(horizontal = 20.dp, vertical = 10.dp)
            ) {
                Text(
                    text = "Esta foto está boa?",
                    style = MaterialTheme.typography.titleMedium,
                    color = ChefIAColors.White,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(ChefIAColors.Black)
                .padding(MaterialTheme.spacing.lg)
        ) {
            ChefIAButton(
                text = "Tirar outra",
                onClick = onRetake,
                modifier = Modifier.weight(1f),
                trailingIcon = {
                    Icon(Icons.Rounded.Close, contentDescription = null)
                }
            )

            Box(modifier = Modifier.size(MaterialTheme.spacing.md))

            ChefIAButton(
                text = "Confirmar",
                onClick = onConfirm,
                modifier = Modifier.weight(1f),
                trailingIcon = {
                    Icon(Icons.Rounded.Check, contentDescription = null)
                }
            )
        }
    }
}

@Preview
@Composable
private fun CameraCapturePreview() {
    ChefIATheme {
        CameraContent(
            state = CameraUiState(),
            hasCameraPermission = true,
            onBack = {},
            onAction = {},
            onTakePicture = {}
        )
    }
}

@Preview
@Composable
private fun CameraReviewPreview() {
    ChefIATheme {
        CameraContent(
            state = CameraUiState(
                capturedImage = createBitmap(100, 100)
            ),
            hasCameraPermission = true,
            onBack = {},
            onAction = {},
            onTakePicture = {}
        )
    }
}

@Preview
@Composable
private fun CameraLoadingPreview() {
    ChefIATheme {
        CameraContent(
            state = CameraUiState(
              isLoading = true
            ),
            hasCameraPermission = true,
            onBack = {},
            onAction = {},
            onTakePicture = {}
        )
    }
}