package com.example.domain.type

sealed class DrawType {
    abstract val title: String
    data class LuckyDraw(override val title: String, val keyword: String): DrawType()
    data class StatisticDraw(override val title: String, val list: List<String>): DrawType()
}