package com.example.mvi_test.screen.qr

import androidx.lifecycle.viewModelScope
import com.example.domain.repository.UserRepository
import com.example.mvi_test.base.BaseViewModel
import com.example.mvi_test.screen.random.state.QRScannerActionState
import com.example.mvi_test.screen.random.state.QRScannerEffectState
import com.example.mvi_test.screen.random.state.QRScannerUIState
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
        }
    }

    fun effectHandler(eventState: QRScannerEffectState){
        viewModelScope.launch {
            when(eventState){
                is QRScannerEffectState.ShowToast -> _sideEffectState.trySend(QRScannerEffectState.ShowToast(eventState.message))
                is QRScannerEffectState.ShowSnackbar -> _sideEffectState.trySend(QRScannerEffectState.ShowSnackbar(eventState.message))
            }
        }
    }

    private fun setRequireCameraPermission(){
        ioScope.launch {
            userRepository.setRequireCameraPermission(true)
        }
    }
}