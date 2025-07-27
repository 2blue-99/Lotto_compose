package com.example.mvi_test.screen.qr

import androidx.annotation.OptIn
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import timber.log.Timber

@Composable
fun QRScannerRouth(modifier: Modifier = Modifier) {

    QRScannerScreen(modifier)
}

@Composable
fun QRScannerScreen(modifier: Modifier = Modifier) {

    // 권한

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Box {
            QRContainer()
        }

        TargetBox()
    }
}

@Composable
fun TargetBox(modifier: Modifier = Modifier) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    Box(
        modifier = Modifier
            .width(screenWidth-100.dp)
            .aspectRatio(1f)
            .border(4.dp, color = Color.White)
    )
}

@Preview
@Composable
private fun TargetBoxPreview() {
    TargetBox()
}

@OptIn(ExperimentalGetImage::class)
@Composable
fun QRContainer(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    var lastScanned by remember { mutableStateOf("") }
    // QR 스캐너 객체 생성 (ML Kit)
    val scanner = remember {
        BarcodeScanning.getClient(
            BarcodeScannerOptions.Builder()
                .setBarcodeFormats(Barcode.FORMAT_QR_CODE)
                .build()
        )
    }

// 1. 카메라 컨트롤러 생성 및 QR 인식 분석기 연결
    val cameraController = remember {
        LifecycleCameraController(context).apply {
            // QR 코드 분석기 설정
            setImageAnalysisAnalyzer(
                ContextCompat.getMainExecutor(context)
            ) { imageProxy ->
                val mediaImage = imageProxy.image
                if (mediaImage != null) {
                    val image = InputImage.fromMediaImage(
                        mediaImage,
                        imageProxy.imageInfo.rotationDegrees
                    )

                    // 인식 결과 처리
                    scanner.process(image)
                        .addOnSuccessListener { barcodes ->
                            for (barcode in barcodes) {
                                val value = barcode.rawValue ?: continue
                                if (value != lastScanned) {
                                    lastScanned = value
                                    Timber.d("성공입니다~~~~~~")
                                    val value = barcode.rawValue // "https://example.com?id=123"
                                    Timber.d("value : $value")
                                }
                            }
                        }
                        .addOnFailureListener {
                            Timber.d("실패 : $it")
                        }
                        .addOnCompleteListener {
                            imageProxy.close()
                        }
                } else {
                    imageProxy.close()
                }
            }
        }
    }.apply {
        bindToLifecycle(lifecycleOwner)
    }

    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { ctx ->
            // Initialize the PreviewView and configure it
            PreviewView(ctx).apply {
                scaleType = PreviewView.ScaleType.FILL_START
                implementationMode = PreviewView.ImplementationMode.COMPATIBLE
                controller = cameraController // Set the controller to manage the camera lifecycle
            }
        },
        onRelease = {
            // Release the camera controller when the composable is removed from the screen
            cameraController.unbind()
        }
    )
}



@Preview
@Composable
private fun QRScreenPreview() {
    QRScannerScreen()
}