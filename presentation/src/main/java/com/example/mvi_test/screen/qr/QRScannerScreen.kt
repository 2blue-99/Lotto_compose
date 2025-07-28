package com.example.mvi_test.screen.qr

import android.Manifest
import androidx.annotation.OptIn
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Surface
import androidx.compose.material.Text
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
import com.example.mvi_test.designsystem.common.BaseDialog
import com.example.mvi_test.designsystem.common.DialogInfo
import com.example.mvi_test.screen.home.state.DialogState
import com.example.mvi_test.screen.home.state.HomeActionState
import com.example.mvi_test.screen.qr.state.QRScannerActionState
import com.example.mvi_test.screen.qr.state.QRScannerUIState
import com.example.mvi_test.ui.theme.CommonStyle
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
    val dialogState by viewModel.dialogState.collectAsStateWithLifecycle()

    QRScannerScreen(
        uiState,
        viewModel::actionHandler,
        dialogState,
        modifier
    )
}

@Composable
fun QRScannerScreen(
    uiState: QRScannerUIState = QRScannerUIState.Loading,
    actionHandler: (QRScannerActionState) -> Unit = {},
    dialogState: DialogState<DialogInfo> = DialogState.Hide,
    modifier: Modifier = Modifier
) {
    var permissionState  by remember { mutableStateOf(false) }


    if(dialogState is DialogState.Show){
        BaseDialog(
            dialogInfo = dialogState.data,
            onDismiss = { actionHandler(QRScannerActionState.HideDialog) },
            onConfirm = {  },
            onCancel = { actionHandler(QRScannerActionState.HideDialog) },
        )
    }

    when(uiState){
        is QRScannerUIState.Success -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black),
                contentAlignment = Alignment.Center
            ) {
                if(permissionState) {
                    QRContainer()
                }

                TargetBox(
                    permissionState
                )
            }

            // 권한 체크가 위에 있을 경우 이후 로직이 안보임
            CheckPermission(
                uiState.isRequiredCamera,
                actionHandler,
                { permissionState = it }
            )
        }
        else -> {}
    }
}

@kotlin.OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CheckPermission(
    isRequiredCamera: Boolean,
    actionHandler: (QRScannerActionState) -> Unit,
    onChangePermission: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val permissionState = rememberPermissionState(Manifest.permission.CAMERA)
    // 요청 자체가 안되는 상황을 파악하기 위함
    var requestState by rememberSaveable { mutableStateOf(isRequiredCamera) }

    LaunchedEffect(Unit) {
        // 권한 비허용 + 한번 이상 거부 X + 요청한적 없는 경우
        if(!permissionState.status.isGranted && !permissionState.status.shouldShowRationale && !requestState){
            permissionState.launchPermissionRequest()
            actionHandler(QRScannerActionState.UpdateRequireCameraPermission)
            Timber.d("권한 비허용 + 한번 이상 거부 X + 요청한적 없는 경우")
        // 권한 비허용 + 헌번 이상 거부 O
        }else if(!permissionState.status.isGranted && requestState){
            // TODO 팝업창 만들어야 함
            actionHandler(QRScannerActionState.UpdateRequireCameraPermission)
            actionHandler(QRScannerActionState.ShowDialog(DialogInfo.CAMERA_PERMISSION))
            Timber.d("권한 비허용 + 사용자가 여러번 거절한 상태")
        }else {
            Timber.d("권한 허용")
            onChangePermission(true)
        }
    }
}

@Composable
fun TargetBox(
    isGrant: Boolean = false,
    modifier: Modifier = Modifier
) {
    // 화면 너비 측정
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    // 화면 노출 문구
    val infoText = if(isGrant) "용지의 QR 코드를\n화면 중앙에 스캔해주세요." else "카메라 권한이 필요합니다."
    Box(
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = infoText,
                style = CommonStyle.text20BoldShadow,
                color = Color.White
            )
            Box(
                modifier = Modifier
                    .width(screenWidth - 100.dp)
                    .aspectRatio(1f)
                    .border(4.dp, color = Color.White)
            )
        }
    }
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