package com.example.data.util

import com.example.data.local.entity.LottoEntity
import com.example.domain.model.LottoRecode

object Mapper {
    fun LottoRecode.toEntity(): LottoEntity = LottoEntity(id, test)
}