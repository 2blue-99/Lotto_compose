package com.example.domain.model

import com.example.domain.type.DrawType.Companion.TYPE_LUCKY

/**
 * 랜덤 추첨 - 로또 아이템
 */
data class LottoItem(
    val id: Int = 0,
    val sequence: String = "A", // A~B 순서
    val sum: String = "100", // 총합
    val oddEndEvent: String = "3:3", // 홀짝 3:3
    val highEndLow: String = "2:4", // 고저 2:4
    val drawList: List<String> = listOf("7","7","7","7","7","7") // 보너스를 제외한 6개 추첨 결과
){
    /**
     * 총합 / 홀짝 / 고처 반환
     */
    fun toLottoInfo() = "총합 $sum   홀짝 $oddEndEvent   고저 $highEndLow"
}