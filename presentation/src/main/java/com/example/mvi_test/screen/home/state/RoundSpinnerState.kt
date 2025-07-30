package com.example.mvi_test.screen.home.state

sealed class BaseDialogState<out T> {
    data object Hide: BaseDialogState<Nothing>()
    data class Show<T>(val data: T): BaseDialogState<T>()
}