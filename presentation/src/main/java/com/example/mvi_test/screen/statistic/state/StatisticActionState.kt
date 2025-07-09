package com.example.mvi_test.screen.statistic.state

sealed class StatisticActionState {
    data class OnClickDelete(val saveDate: String): StatisticActionState()
    data object OnClickShare : StatisticActionState()
}