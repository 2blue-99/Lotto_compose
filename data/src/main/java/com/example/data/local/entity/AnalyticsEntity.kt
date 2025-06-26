package com.example.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.domain.model.LottoRecode

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
