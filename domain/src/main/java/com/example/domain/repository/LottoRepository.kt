package com.example.domain.repository

import com.example.domain.model.LottoRound
import com.example.domain.util.ResourceState
import kotlinx.coroutines.flow.Flow

interface LottoRepository {
    fun getLottoDao(): Flow<List<LottoRound>>

//    suspend fun updateLotto(data: LottoRecode)

    suspend fun getLottoData(round: Int): ResourceState<LottoRound>
}