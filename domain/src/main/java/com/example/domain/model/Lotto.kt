package com.example.domain.model

data class Lotto(
    val drawNumber: Int = 7777, // 회차 번호
    val drawDate: String = "2025.01.01 20:35:00", // 추첨일
    val totalSellAmount: Long = 100_000_000, // 총 판매 금액
    val firstWinTotalAmount: Long = 100_000_000, // 1등 총 당첨금
    val firstWinCount: Int = 1, // 1등 당첨자 수
    val firstWinPerAmount: Long = 100_000_000, // 1등 1명당 당첨금
    val drwtNo1: Int = 7,
    val drwtNo2: Int = 7,
    val drwtNo3: Int = 7,
    val drwtNo4: Int = 7,
    val drwtNo5: Int = 7,
    val drwtNo6: Int = 7,
    val bnusNo: Int = 7,
)
