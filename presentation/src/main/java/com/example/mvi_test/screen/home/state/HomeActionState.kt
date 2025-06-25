package com.example.mvi_test.screen.home.state

sealed class HomeActionState {
    data object OnBackClick: HomeActionState()
}