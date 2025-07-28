package com.example.mvi_test.screen.qr

import android.Manifest
import android.widget.Toast
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
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LifecycleResumeEffect
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.domain.type.DialogType
import com.example.domain.util.CommonMessage
import com.example.mvi_test.designsystem.common.BaseDialog
import com.example.mvi_test.designsystem.common.VerticalSpacer
import com.example.mvi_test.screen.home.state.DialogState
import com.example.mvi_test.screen.qr.state.QRScannerActionState
import com.example.mvi_test.screen.qr.state.QRScannerEffectState
import com.example.mvi_test.screen.qr.state.QRScannerUIState
import com.example.mvi_test.ui.theme.CommonStyle
import com.example.mvi_test.util.openBrowser
import com.example.mvi_test.util.openSetting
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber

@Composable
fun QRScannerRouth(
    popBackStack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: QRScannerViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val dialogState by viewModel.dialogState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.sideEffectState.collectLatest { effect ->
            when (effect) {
                is QRScannerEffectState.ShowToast -> {
                    Toast.makeText(context, effect.message.message, Toast.LENGTH_SHORT).show()
                }
                is QRScannerEffectState.ShowSnackbar -> {}
                is QRScannerEffectState.PopBackStack -> { popBackStack() }
            }
        }
    }

    QRScannerScreen(
        uiState,
        viewModel::actionHandler,
        viewModel::effectHandler,
        dialogState,
        modifier
    )
}

@Composable
fun QRScannerScreen(
    uiState: QRScannerUIState = QRScannerUIState.Loading,
    actionHandler: (QRScannerActionState) -> Unit,
    effectHandler: (QRScannerEffectState) -> Unit,
    dialogState: DialogState<DialogType> = DialogState.Hide,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    var permissionState  by remember { mutableStateOf(false) }

    if(dialogState is DialogState.Show){
        BaseDialog(
            dialogType = dialogState.data,
            onDismiss = {},
            onConfirm = {
                actionHandler(QRScannerActionState.HideDialog)
                context.openSetting()
            },
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
                    QRContainer(
                        onScanSuccess = {},
                        effectHandler = effectHandler
                    )
                }

                TargetBox(
                    permissionState
                )
            }

            // 권한 체크가 위에 있을 경우 이후 로직이 안보임
            CheckPermission(
                isRequiredCamera = uiState.isRequiredCamera,
                actionHandler = actionHandler,
                onGrantPermission = { permissionState = it }
            )
        }
        else -> {}
    }
}

@kotlin.OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CheckPermission(
    isRequiredCamera: Boolean, // 과거에 권한 요청한 적이 있는지 여부
    actionHandler: (QRScannerActionState) -> Unit,
    onGrantPermission: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val permissionState = rememberPermissionState(Manifest.permission.CAMERA)
    // 요청 자체가 안되는 상황을 파악하기 위해 Data Store 로 저장한 데이터 조회
    val requestState by rememberSaveable { mutableStateOf(isRequiredCamera) }

    // onResume 상태일 때마다 동작
    LifecycleResumeEffect(Unit) {
        // 권한 비허용 + 한번 이상 거부 X + 요청한적 없는 경우
        if(!permissionState.status.isGranted && !permissionState.status.shouldShowRationale && !requestState){
            permissionState.launchPermissionRequest()
            actionHandler(QRScannerActionState.UpdateRequireCameraPermission)
        // 권한 비허용 + 헌번 이상 거부 O
        }else if(!permissionState.status.isGranted && requestState){
            actionHandler(QRScannerActionState.UpdateRequireCameraPermission)
            actionHandler(QRScannerActionState.ShowDialog(DialogType.CAMERA_PERMISSION))
        // 권한 허용
        }else {
            onGrantPermission(true)
        }
        onPauseOrDispose {
            Timber.d("onPauseOrDispose")
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
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = infoText,
                style = CommonStyle.text20BoldShadow,
                color = Color.White,
                textAlign = TextAlign.Center
            )
            VerticalSpacer(10.dp)
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
    onScanSuccess: () -> Unit,
    effectHandler: (QRScannerEffectState) -> Unit,
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
                                    barcode.rawValue?.let { url ->
                                        context.openBrowser(url)
                                        effectHandler(QRScannerEffectState.PopBackStack)
                                    } ?: { effectHandler(QRScannerEffectState.ShowToast(CommonMessage.SCANNER_FAIL)) }
                                    return@addOnSuccessListener
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

    // 카메라 프리뷰
    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { ctx ->
            PreviewView(ctx).apply {
                scaleType = PreviewView.ScaleType.FILL_START
                implementationMode = PreviewView.ImplementationMode.COMPATIBLE
                controller = cameraController
            }
        },
        onRelease = {
            cameraController.unbind()
        }
    )
}



@Preview
@Composable
private fun QRScreenPreview() {
    QRScannerScreen(
        QRScannerUIState.Loading,{},{}
    )
}