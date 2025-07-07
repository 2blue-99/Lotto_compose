package com.example.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

// 로또 추첨 화면 > 선택 저장 로또 DB
@Entity(tableName = "lotto")
data class LottoRecodeEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
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
    fun toDomain(){

    }
}
