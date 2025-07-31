package com.lucky_lotto.mvi_test.screen.qr.state

import com.lucky_lotto.domain.type.DialogType

sealed interface PermissionDialogState {
    data object Hide: PermissionDialogState
    data class Show(val dialogType: DialogType): PermissionDialogState
}

sealed interface AdDialogState {
    data object Hide: AdDialogState
    data class Show(val dialogType: DialogType, val url: String): AdDialogState
}