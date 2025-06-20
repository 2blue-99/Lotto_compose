package com.example.mvi_test.screen.random.state

sealed class RandomEventState {
    data class OnMakeClick(val a: String): RandomEventState()
    data object OnRefresh: RandomEventState()
}

