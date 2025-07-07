package com.example.domain.model

/**
 * 랜덤 추첨 - 로또 아이템
 */
data class LottoItem(
    val sequence: String = "A", // A~B 순서
    val sum: String = "100", // 총합
    val oddEndEvent: String = "3:3", // 홀짝 3:3
    val highEndLow: String = "2:4", // 고저 2:4
    val drawList: List<String> = listOf("7","7","7","7","7","7") // 보너스를 제외한 6개 추첨 결과
)