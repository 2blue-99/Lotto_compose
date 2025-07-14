package com.example.domain.type

enum class RangeType(
    val month: Int,
) {
    ONE_MONTH(1),
    SIX_MONTH(6),
    ONE_YEAR(12),
    THREE_YEAR(36),
    TEN_YEAR(120),
}