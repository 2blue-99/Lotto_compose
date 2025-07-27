package com.example.data.util

import com.example.data.local.entity.KeywordEntity
import com.example.data.local.entity.LottoRecodeEntity
import com.example.domain.model.Keyword
import com.example.domain.model.LottoItem

object Mapper {
    fun Keyword.toEntity(): KeywordEntity = KeywordEntity(id, title)

    /**
     * @param type : 추첨 타입 (행운, 통계)
     * @param data : 추첨 데이터 (키워드, 필수 번호)
     *
     */
    fun LottoItem.toLottoRecodeReEntity(type: String, data: String, time: String) =
        LottoRecodeEntity(
            id = 0,
            drawType = type,
            drawData = data,
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