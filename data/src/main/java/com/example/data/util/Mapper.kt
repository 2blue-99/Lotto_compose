package com.example.data.util

import com.example.data.local.entity.KeywordEntity
import com.example.data.local.entity.LottoRecodeEntity
import com.example.domain.model.Keyword
import com.example.domain.model.LottoItem

object Mapper {
//    fun LottoRecode.toEntity(): LottoEntity = LottoEntity(
//        id = id,
//        drawNumber = drawNumber,
//        drawDate,
//        totalSellAmount,
//        firstWinTotalAmount,
//        firstWinCount,
//        firstWinPerAmount,
//        drwtNo1,
//        drwtNo2,
//        drwtNo3,
//        drwtNo4,
//        drwtNo5,
//        drwtNo6,
//        bnusNo
//    )

    fun Keyword.toEntity(): KeywordEntity = KeywordEntity(id, title)

    fun LottoItem.toLottoRecodeReEntity(time: String) =
        LottoRecodeEntity(
            id = 0,
            drawType = "",
            drawData = "",
            saveDate = time,
            sequence = sequence,
            sum = sum,
            oddEndEvent = oddEndEvent,
            highEndLow = highEndLow,
            drwtNo1 = drawList[0],
            drwtNo2 = drawList[1],
            drwtNo3 = drawList[2],
            drwtNo4 = drawList[3],
            drwtNo5 = drawList[4],
            drwtNo6 = drawList[5],
        )
}