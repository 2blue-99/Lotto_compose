package com.example.data.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.domain.model.Lotto


@Entity(tableName = "lotto")
data class LottoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val test: String
){
    fun toDomain(): Lotto = Lotto(id, test)
}
