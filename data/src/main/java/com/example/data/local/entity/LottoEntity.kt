package com.example.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.domain.model.LottoRecode


@Entity(tableName = "lotto")
data class LottoEntity(
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
    fun toDomain(): LottoRecode = LottoRecode(
        id,
        drawNumber,
        drawDate,
        totalSellAmount,
        firstWinTotalAmount,
        firstWinCount,
        firstWinPerAmount,
        drwtNo1,
        drwtNo2,
        drwtNo3,
        drwtNo4,
        drwtNo5,
        drwtNo6,
        bnusNo
    )
}
