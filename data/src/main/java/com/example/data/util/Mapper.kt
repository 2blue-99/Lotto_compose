package com.example.data.util

import com.example.data.local.entity.KeywordEntity
import com.example.data.local.entity.LottoEntity
import com.example.domain.model.Keyword
import com.example.domain.model.LottoRecode

object Mapper {
    fun LottoRecode.toEntity(): LottoEntity = LottoEntity(
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

    fun Keyword.toEntity(): KeywordEntity = KeywordEntity(id, title)
}