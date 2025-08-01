package com.lucky_lotto.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.lucky_lotto.data.util.Utils.formatComma
import com.lucky_lotto.data.util.Utils.formatDate
import com.lucky_lotto.domain.model.LottoRound


@Entity(tableName = "lotto_round")
data class LottoRoundEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val drawNumber: Int, // 회차 번호
    val drawDate: String, // 추첨일
    val totalSellAmount: Long, // 총 판매 금액
    val firstWinTotalAmount: Long, // 1등 총 당첨금
    val firstWinCount: Int, // 1등 당첨자 수
    val firstWinPerAmount: Long, // 1등 1명당 당첨금
    val drwtNo1: Int,
    val drwtNo2: Int,
    val drwtNo3: Int,
    val drwtNo4: Int,
    val drwtNo5: Int,
    val drwtNo6: Int,
    val bnusNo: Int,
) {
    fun toDomain(): LottoRound = LottoRound(
        drawNumber.toString(),
        drawDate.formatDate(),
        totalSellAmount.formatComma(),
        firstWinTotalAmount.formatComma(),
        firstWinCount.toString(),
        firstWinPerAmount.formatComma(),
        drwtNo1.toString(),
        drwtNo2.toString(),
        drwtNo3.toString(),
        drwtNo4.toString(),
        drwtNo5.toString(),
        drwtNo6.toString(),
        bnusNo.toString()
    )
}
