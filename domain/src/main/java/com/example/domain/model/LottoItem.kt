package com.example.domain.model

/**
 * 랜덤 추첨 - 로또 아이템
 */
data class LottoItem(
    val sequence: String, // A~B 순서
    val sum: String = "", // 총합
    val oddEndEvent: String = "", // 홀짝 3:3
    val highEndLow: String = "", // 고저 2:4
    val drawList: List<String>
)