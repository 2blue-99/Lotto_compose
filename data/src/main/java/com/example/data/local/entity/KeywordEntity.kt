package com.example.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.domain.model.Keyword

// 로또 추첨 화면 > 선택 저장 로또 DB
@Entity(tableName = "keyword")
data class KeywordEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
){
    fun toDomain(): Keyword = Keyword(id, title)
}
