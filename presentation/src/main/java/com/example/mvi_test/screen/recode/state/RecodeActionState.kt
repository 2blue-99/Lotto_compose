package com.example.mvi_test.screen.recode.state

sealed class RecodeActionState {
    data class OnClickDelete(val targetId: Int): RecodeActionState()
    data object OnClickShare : RecodeActionState()
}