package com.lucky_lotto.data.remote.model

import com.lucky_lotto.data.local.entity.LottoRoundEntity
import com.lucky_lotto.data.util.Utils.toKoreanAmount
import com.lucky_lotto.data.util.Utils.formatDate
import com.lucky_lotto.domain.model.LottoRound
import com.google.gson.annotations.SerializedName

data class LottoApiResponse(
    @SerializedName("resultCode")
    val resultCode: String?,

    @SerializedName("resultMessage")
    val resultMessage: String?,

    @SerializedName("data")
    val data: LottoApiData?
)

data class LottoApiData(
    @SerializedName("list")
    val list: List<LottoResponse>
)

data class LottoResponse(
    @SerializedName("ltEpsd")
    val drawNumber: Int, // 회차 번호

    @SerializedName("ltRflYmd")
    val drawDate: String, // 추첨일 (yyyyMMdd)

    @SerializedName("rlvtEpsdSumNtslAmt")
    val totalSellAmount: Long, // 총 판매 금액

    @SerializedName("rnk1SumWnAmt")
    val firstWinTotalAmount: Long, // 1등 총 당첨금

    @SerializedName("rnk1WnNope")
    val firstWinCount: Int, // 1등 당첨자 수

    @SerializedName("rnk1WnAmt")
    val firstWinPerAmount: Long, // 1등 1명당 당첨금

    @SerializedName("tm1WnNo")
    val drwtNo1: Int,

    @SerializedName("tm2WnNo")
    val drwtNo2: Int,

    @SerializedName("tm3WnNo")
    val drwtNo3: Int,

    @SerializedName("tm4WnNo")
    val drwtNo4: Int,

    @SerializedName("tm5WnNo")
    val drwtNo5: Int,

    @SerializedName("tm6WnNo")
    val drwtNo6: Int,

    @SerializedName("bnsWnNo")
    val bnusNo: Int,
) {
    fun toDomain(): LottoRound {
        return LottoRound(
            drawNumber.toString(),
            drawDate.formatDate(),
            totalSellAmount.toKoreanAmount(),
            firstWinTotalAmount.toKoreanAmount(),
            firstWinCount.toString(),
            firstWinPerAmount.toKoreanAmount(),
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
        // DB 날짜 비교를 위해 "yyyy-MM-dd" 형식으로 정규화
        // 신 API: "20260321" → "2026-03-21" / 구 API: "2026-03-21" → 그대로
        val normalizedDate = if (drawDate.contains("-")) drawDate
            else "${drawDate.substring(0, 4)}-${drawDate.substring(4, 6)}-${drawDate.substring(6, 8)}"
        return LottoRoundEntity(
            id = 0,
            drawNumber = drawNumber,
            drawDate = normalizedDate,
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
