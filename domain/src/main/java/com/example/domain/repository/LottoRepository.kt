package com.example.domain.repository

import com.example.domain.model.LottoItem
import com.example.domain.model.LottoRound
import com.example.domain.util.ResourceState
import kotlinx.coroutines.flow.Flow

interface LottoRepository {
    fun getLottoDao(): Flow<List<LottoRound>>
    suspend fun insertLottoDao(list:List<LottoItem>)

//    suspend fun updateLotto(data: LottoRecode)

    suspend fun requestLottoData(round: Int): ResourceState<LottoRound>
}