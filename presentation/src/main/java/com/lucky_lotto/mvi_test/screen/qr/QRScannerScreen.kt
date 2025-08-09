package com.lucky_lotto.mvi_test.screen.qr

import android.Manifest
import android.os.CountDownTimer
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
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LifecycleResumeEffect
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import com.lucky_lotto.domain.type.DialogType
import com.lucky_lotto.domain.util.CommonMessage
import com.lucky_lotto.domain.util.Constants.AD_DIALOG_WAIT_TIME
import com.lucky_lotto.mvi_test.designsystem.common.BaseDialog
import com.lucky_lotto.mvi_test.designsystem.common.VerticalSpacer
import com.lucky_lotto.mvi_test.screen.qr.state.AdDialogState
import com.lucky_lotto.mvi_test.screen.qr.state.PermissionDialogState
import com.lucky_lotto.mvi_test.screen.qr.state.QRScannerActionState
import com.lucky_lotto.mvi_test.screen.qr.state.QRScannerEffectState
import com.lucky_lotto.mvi_test.screen.qr.state.QRScannerUIState
import com.lucky_lotto.mvi_test.ui.theme.CommonStyle
import com.lucky_lotto.mvi_test.util.openBrowser
import com.lucky_lotto.mvi_test.util.openSetting
import com.lucky_lotto.mvi_test.util.startVibrate
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

@Composable
fun QRScannerRouth(
    popBackStack: () -> Unit,
    showFrontPageAd: () -> StateFlow<Boolean>,
    modifier: Modifier = Modifier,
    viewModel: QRScannerViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val permissionDialogState by viewModel.permissionDialogState.collectAsStateWithLifecycle()
    val adDialogState by viewModel.adDialogState.collectAsStateWithLifecycle()

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

    // TODO 더 나은 구조가 있을지 고민
    // 권한 확인 다이알로그
    if(permissionDialogState is PermissionDialogState.Show){
        BaseDialog(
            dialogType = (permissionDialogState as PermissionDialogState.Show).dialogType,
            onDismiss = {},
            onConfirm = {
                viewModel.actionHandler(QRScannerActionState.HidePermissionDialog)
                context.openSetting()
            },
            onCancel = { viewModel.actionHandler(QRScannerActionState.HidePermissionDialog) },
        )
    }

    // 광고 확인 다이알로그
    if(adDialogState is AdDialogState.Show){
        var count by remember { mutableLongStateOf(AD_DIALOG_WAIT_TIME) }
        val dialog = adDialogState as AdDialogState.Show

        // 팝업 노출 후 자동 광고 시작 트리거
        LaunchedEffect(Unit) {
            object : CountDownTimer(AD_DIALOG_WAIT_TIME, 100) {
                override fun onTick(millisUntilFinished: Long) {
                    count -= 100
                }
                override fun onFinish() {
                    // 다이알로그 숨기기
                    viewModel.actionHandler(QRScannerActionState.HideAdDialog)
                    coroutineScope.launch {
                        // 광고 송출 시작 & 완료 Flow 대기
                        showFrontPageAd().collectLatest { state ->
                            // 완료 시
                            if (state) {
                                context.openBrowser(dialog.url)
                                viewModel.effectHandler(QRScannerEffectState.PopBackStack)
                                return@collectLatest
                            }
                        }
                    }
                }
            }.start()
        }

        BaseDialog(
            dialogType = dialog.dialogType,
            autoConfirmWaitTime = count,
            onDismiss = {},
            onConfirm = {},
            onCancel = { viewModel.effectHandler(QRScannerEffectState.PopBackStack) }
        )
    }

    QRScannerScreen(
        uiState,
        viewModel::actionHandler,
        viewModel::effectHandler,
        modifier
    )
}

@Composable
fun QRScannerScreen(
    uiState: QRScannerUIState = QRScannerUIState.Loading,
    actionHandler: (QRScannerActionState) -> Unit,
    effectHandler: (QRScannerEffectState) -> Unit,
    modifier: Modifier = Modifier
) {
    var permissionState  by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        if(permissionState) {
            QRContainer(
                onScanSuccess = { url ->
                    actionHandler(QRScannerActionState.ShowAdDialog(DialogType.FRONT_PAGE_AD, url))
                },
                effectHandler = effectHandler
            )
        }

        TargetBox(
            isGrant = permissionState,
        )
    }

    // 권한 체크가 위에 있을 경우 이후 로직이 안보임
    if(uiState is QRScannerUIState.Success) {
        CheckPermission(
            isRequiredCamera = uiState.isRequiredCamera,
            actionHandler = actionHandler,
            onGrantPermission = { permissionState = it }
        )
    }
}

@kotlin.OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CheckPermission(
    isRequiredCamera: Boolean, // 과거 권한 요청 여부
    actionHandler: (QRScannerActionState) -> Unit,
    onGrantPermission: (Boolean) -> Unit,
) {
    val permissionState = rememberPermissionState(Manifest.permission.CAMERA)
    // 권한 요청 시 사용자가 여러번 거부할 경우 요청 자체가 안됨
    // 요청이 안되는 상태를 조회할 수 없기 때문에, 한번 이상 요청할 경우 앱 자체적으로 요청 여부 저장으로 대응
    val requestState by rememberSaveable { mutableStateOf(isRequiredCamera) } // 과거 권한 요청 여부

    // onResume 상태일 때마다 동작
    LifecycleResumeEffect(Unit) {
        // 권한 없음 + 요청한적 없는 경우
        if(!permissionState.status.isGranted && !permissionState.status.shouldShowRationale && !requestState){
            actionHandler(QRScannerActionState.UpdateRequireCameraPermission) // 요청 여부 업데이트
            permissionState.launchPermissionRequest() // 권한 요청
        // 권한 비허용 + 헌번 이상 거부 O
        }else if(!permissionState.status.isGranted && requestState){
            actionHandler(QRScannerActionState.ShowPermissionDialog(DialogType.CAMERA_PERMISSION)) // 설정 화면으로 이동하도록 유도 다이알로그 노출
        // 권한 요청 -> 거부
        }else if(!permissionState.status.isGranted){
            // TODO 뒤로가기 처리하는것도 괜찮은 것 같음
        // 권한 요청 -> 허용
        }else{
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
    onScanSuccess: (String) -> Unit,
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
                                        context.startVibrate()
                                        onScanSuccess(url)
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