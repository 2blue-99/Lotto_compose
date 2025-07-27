package com.example.mvi_test.screen.qr

import android.Manifest
import androidx.annotation.OptIn
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mvi_test.screen.random.state.QRScannerActionState
import com.example.mvi_test.screen.random.state.QRScannerUIState
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import timber.log.Timber

@Composable
fun QRScannerRouth(
    modifier: Modifier = Modifier,
    viewModel: QRScannerViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    QRScannerScreen(
        uiState,
        viewModel::actionHandler,
        modifier
    )
}

@Composable
fun QRScannerScreen(
    uiState: QRScannerUIState = QRScannerUIState.Loading,
    actionHandler: (QRScannerActionState) -> Unit = {},
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        color = Color.Black
    ) {
        if(uiState is QRScannerUIState.Success) {
            CheckPermission(
                actionHandler,
                uiState.isRequiredCamera
            )

            QRContainer()

            TargetBox()
        }
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ){
    }
}

@kotlin.OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CheckPermission(
    actionHandler: (QRScannerActionState) -> Unit,
    isRequiredCamera: Boolean,
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()
    val permissionState = rememberPermissionState(Manifest.permission.CAMERA)
    // 요청 자체가 안되는 상황을 파악하기 위함
    var requestState by rememberSaveable { mutableStateOf(isRequiredCamera) }

    LaunchedEffect(Unit) {
        // 권한 비허용 + 한번 이상 거부 X + 요청한적 없는 경우
        if(!permissionState.status.isGranted && !permissionState.status.shouldShowRationale && !requestState){
            permissionState.launchPermissionRequest()
            actionHandler(QRScannerActionState.UpdateRequireCameraPermission)
            Timber.d("권한 비허용 + 한번 이상 거부 X + 요청한적 없는 경우")
        // 권한 비허용 + 사용자가 여러번 거절한 상태
        }else if(!permissionState.status.isGranted && requestState){
            // TODO 팝업창 만들어야 함
            actionHandler(QRScannerActionState.UpdateRequireCameraPermission)
            Timber.d("권한 비허용 + 사용자가 여러번 거절한 상태")
        }else {
            Timber.d("권한 허용")
        }
    }
}

@Composable
fun TargetBox(modifier: Modifier = Modifier) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    Box(
        modifier = Modifier
            .width(screenWidth - 100.dp)
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
fun QRContainer(
    modifier: Modifier = Modifier
) {
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