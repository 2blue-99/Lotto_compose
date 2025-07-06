package com.example.mvi_test.screen.random.state

sealed class RandomActionState {
    data class AddKeyword(val title: String): RandomActionState()
    data class DeleteKeyword(val targetId: Int): RandomActionState()
    data class OnClickDraw(val keyword: String): RandomActionState()
//    data class OnClickSave(val list: List<List<String>>): RandomActionState()
}