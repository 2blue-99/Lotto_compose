package com.example.domain.repository

import com.example.domain.model.Lotto
import kotlinx.coroutines.flow.Flow

interface LottoRepository {
    fun getLotto(): Flow<List<Lotto>>

    suspend fun updateLotto(data: Lotto)

    suspend fun requestLotto()
}