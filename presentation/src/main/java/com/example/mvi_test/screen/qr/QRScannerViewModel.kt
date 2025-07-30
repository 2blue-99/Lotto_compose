package com.example.mvi_test.screen.qr

import androidx.lifecycle.viewModelScope
import com.example.domain.repository.UserRepository
import com.example.mvi_test.base.BaseViewModel
import com.example.mvi_test.screen.qr.state.AdDialogState
import com.example.mvi_test.screen.qr.state.PermissionDialogState
import com.example.mvi_test.screen.qr.state.QRScannerActionState
import com.example.mvi_test.screen.qr.state.QRScannerEffectState
import com.example.mvi_test.screen.qr.state.QRScannerUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QRScannerViewModel @Inject constructor(
    private val userRepository: UserRepository
): BaseViewModel() {
    private val _sideEffectState = Channel<QRScannerEffectState>()
    val sideEffectState = _sideEffectState.receiveAsFlow()
    val uiState = MutableStateFlow<QRScannerUIState>(QRScannerUIState.Loading)
    val permissionDialogState = MutableStateFlow<PermissionDialogState>(PermissionDialogState.Hide)
    val adDialogState = MutableStateFlow<AdDialogState>(AdDialogState.Hide)

    init {
        modelScope.launch {
            userRepository.isRequireCameraPermission.collect {
                uiState.emit(QRScannerUIState.Success(it))
            }
        }
    }

    fun actionHandler(action: QRScannerActionState){
        when(action){
            is QRScannerActionState.UpdateRequireCameraPermission -> { setRequireCameraPermission() }

            is QRScannerActionState.ShowPermissionDialog -> { permissionDialogState.value = PermissionDialogState.Show(action.dialogType) }
            is QRScannerActionState.HidePermissionDialog -> { permissionDialogState.value = PermissionDialogState.Hide }

            is QRScannerActionState.ShowAdDialog -> { adDialogState.value = AdDialogState.Show(action.dialogType, action.url) }
            is QRScannerActionState.HideAdDialog -> { adDialogState.value = AdDialogState.Hide }
        }
    }

    fun effectHandler(eventState: QRScannerEffectState){
        viewModelScope.launch {
            when(eventState){
                else -> { _sideEffectState.send(eventState) }
            }
        }
    }

    private fun setRequireCameraPermission(){
        ioScope.launch {
            userRepository.setRequireCameraPermission(true)
        }
    }
}