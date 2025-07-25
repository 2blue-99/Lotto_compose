package com.example.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.domain.model.LottoItem
import com.example.domain.model.LottoRecode

// 로또 추첨 화면 > 선택 저장 로또 DB
@Entity(tableName = "lotto_recode")
data class LottoRecodeEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val drawType: String, // 추첨 타입 (행운, 통계)
    val drawData: String, // 추첨 데이터 (행운 키워드, 통계 리스트)
    val saveDate: String, // 저장일 + 그룹 아이디 역할
    val sequence: String, // A~B 순서
    val sum: String = "", // 총합
    val oddEndEvent: String = "", // 홀짝 3:3
    val highEndLow: String = "", // 고저 2:4
    val drwtNo1: String,
    val drwtNo2: String,
    val drwtNo3: String,
    val drwtNo4: String,
    val drwtNo5: String,
    val drwtNo6: String,
) {
    fun toDomain(): LottoItem =
        LottoItem(
            id = id,
            sequence = sequence,
            sum = sum,
            oddEndEvent = oddEndEvent,
            highEndLow = highEndLow,
            drawList = listOf(drwtNo1, drwtNo2, drwtNo3, drwtNo4, drwtNo5, drwtNo6)
        )
//    fun toDomain(): LottoRecode =
//        LottoRecode(
//            id = id,
//            saveDate = saveDate,
//            lottoItem = LottoItem(
//                sequence = sequence,
//                sum = sum,
//                oddEndEvent = oddEndEvent,
//                highEndLow = highEndLow,
//                drawList = listOf(drwtNo1, drwtNo2, drwtNo3, drwtNo4, drwtNo5, drwtNo6)
//            ),
//        )
}
