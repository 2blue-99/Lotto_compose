package com.example.mvi_test.screen.random.state

import com.example.domain.model.LottoItem

sealed class RandomActionState {
    data class AddKeyword(val title: String) : RandomActionState()
    data class DeleteKeyword(val targetId: Int) : RandomActionState()
    data class OnClickDraw(val keyword: String) : RandomActionState()
    data class OnClickSave(
        val drawKeyword: String,
        val list: List<LottoItem>
    ) : RandomActionState()
}