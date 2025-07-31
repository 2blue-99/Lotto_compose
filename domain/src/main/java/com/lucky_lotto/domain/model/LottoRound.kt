package com.lucky_lotto.domain.model

/**
 * 홈 - 로또 회차 정보
 */
data class LottoRound(
    val drawNumber: String = "7777", // 회차 번호
    val drawDate: String = "2025.01.01", // 추첨일
    val totalSellAmount: String = "100,000,000", // 총 판매 금액
    val firstWinTotalAmount: String = "100,000,000", // 1등 총 당첨금
    val firstWinCount: String = "1명", // 1등 당첨자 수
    val firstWinPerAmount: String = "100,000,000", // 1등 1명당 당첨금
    val drwtNo1: String = "7",
    val drwtNo2: String = "7",
    val drwtNo3: String = "7",
    val drwtNo4: String = "7",
    val drwtNo5: String = "7",
    val drwtNo6: String = "7",
    val bnusNo: String = "7",
)
