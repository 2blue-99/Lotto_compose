package com.example.data.remote.model

import com.example.data.local.entity.LottoRoundEntity
import com.example.data.util.Utils.formatComma
import com.example.data.util.Utils.formatDate
import com.example.domain.model.LottoRound
import com.google.gson.annotations.SerializedName

data class LottoResponse(
    @SerializedName("returnValue")
    val responseState: String, // 응답 상태

    @SerializedName("drwNo")
    val drawNumber: Int, // 회차 번호

    @SerializedName("drwNoDate")
    val drawDate: String, // 추첨일

    @SerializedName("totSellamnt")
    val totalSellAmount: Long, // 총 판매 금액

    @SerializedName("firstWinamnt")
    val firstWinTotalAmount: Long, // 1등 총 당첨금

    @SerializedName("firstPrzwnerCo")
    val firstWinCount: Int, // 1등 당첨자 수

    @SerializedName("firstAccumamnt")
    val firstWinPerAmount: Long, // 1등 1명당 당첨금

    val drwtNo1: Int,
    val drwtNo2: Int,
    val drwtNo3: Int,
    val drwtNo4: Int,
    val drwtNo5: Int,
    val drwtNo6: Int,
    val bnusNo: Int,
) {
    fun toDomain(): LottoRound {
        return LottoRound(
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

    fun toLottoRoundEntity(): LottoRoundEntity {
        return LottoRoundEntity(
            id = 0,
            drawNumber = drawNumber,
            drawDate = drawDate,
            totalSellAmount = totalSellAmount,
            firstWinTotalAmount = firstWinTotalAmount,
            firstWinCount = firstWinCount,
            firstWinPerAmount = firstWinPerAmount,
            drwtNo1 = drwtNo1,
            drwtNo2 = drwtNo2,
            drwtNo3 = drwtNo3,
            drwtNo4 = drwtNo4,
            drwtNo5 = drwtNo5,
            drwtNo6 = drwtNo6,
            bnusNo = bnusNo
        )
    }
}
