package com.example.mvi_test.screen.result.state

sealed class ResultEventState {
    data class OnMakeClick(val a: String): ResultEventState()
    data object OnRefresh: ResultEventState()
}

