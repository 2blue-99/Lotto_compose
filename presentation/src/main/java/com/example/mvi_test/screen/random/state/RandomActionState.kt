package com.example.mvi_test.screen.random.state

sealed class RandomActionState {
    data object OnBackClick: RandomActionState()
    data class AddKeyword(val title: String): RandomActionState()
    data class DeleteKeyword(val targetId: Int): RandomActionState()
}