package com.example.domain.type

sealed interface DrawType {
    val tagName: String

    data class LuckyDraw(
        override val tagName: String = TYPE_LUCKY,
        val keyword: String
    ): DrawType
    data class StatisticDraw(
        override val tagName: String = TYPE_STATISTIC,
        val list: String
    ): DrawType

    companion object {
        const val TYPE_LUCKY = "행운"
        const val TYPE_STATISTIC = "통계"
    }
}