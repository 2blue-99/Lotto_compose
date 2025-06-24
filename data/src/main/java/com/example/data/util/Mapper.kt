package com.example.data.util

import com.example.data.room.entity.LottoEntity
import com.example.domain.model.Lotto

object Mapper {
    fun Lotto.toEntity(): LottoEntity = LottoEntity(id, test)
}