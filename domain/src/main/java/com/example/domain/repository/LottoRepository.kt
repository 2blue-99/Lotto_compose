package com.example.domain.repository

import com.example.domain.model.Lotto
import com.example.domain.model.LottoRecode
import com.example.domain.util.ResourceState
import kotlinx.coroutines.flow.Flow

interface LottoRepository {
    fun getLottoDao(): Flow<List<LottoRecode>>

    suspend fun updateLotto(data: LottoRecode)

    suspend fun getLottoData(round: Int): ResourceState<Lotto>
}