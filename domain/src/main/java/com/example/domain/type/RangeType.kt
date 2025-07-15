package com.example.domain.type

enum class RangeType(
    val monthText: String,
    val monthValue: Int,
) {
    // 현재 순서 중요

    TEN_YEAR("10년", 120),
    THREE_YEAR("3년", 36),
    ONE_YEAR("1년", 12),
    SIX_MONTH("6개월", 6),
    THREE_MONTH("3개월", 3);

    fun findIndex(): Int {
        return entries.indexOf(this)
    }

    companion object {
        fun toList(): List<RangeType>  {
            return entries.toList()
        }
    }
}