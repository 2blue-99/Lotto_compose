package com.example.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

// 로또 추첨 화면 > 선택 저장 로또 DB
@Entity(tableName = "lotto")
data class LottoRecodeEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val saveDate: String, // 저장일
    val sequence: String, // A~B 순서
    val drwtNo1: Int,
    val drwtNo2: Int,
    val drwtNo3: Int,
    val drwtNo4: Int,
    val drwtNo5: Int,
    val drwtNo6: Int,
    val bnusNo: Int,
) {
    fun toDomain(){

    }
}
