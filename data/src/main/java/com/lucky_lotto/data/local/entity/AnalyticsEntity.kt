package com.lucky_lotto.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

// 로또 분석 정보 저장 DB
@Entity(tableName = "analytics")
data class AnalyticsEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    //
) {
    fun toDomain(){

    }
}
