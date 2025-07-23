package com.example.mvi_test.screen.home.state

sealed class DialogState<out T> {
    data object Hide: DialogState<Nothing>()
    data class Show<T>(val data: T): DialogState<T>()
}