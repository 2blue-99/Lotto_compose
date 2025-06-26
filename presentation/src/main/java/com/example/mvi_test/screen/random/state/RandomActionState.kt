package com.example.mvi_test.screen.random.state

sealed class RandomActionState {
    data object OnBackClick: RandomActionState()
}