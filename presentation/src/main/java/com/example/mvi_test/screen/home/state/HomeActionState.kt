package com.example.mvi_test.screen.home.state

sealed class HomeActionState {
    data class OnChangeRoundPosition(val position: Int): HomeActionState()
}