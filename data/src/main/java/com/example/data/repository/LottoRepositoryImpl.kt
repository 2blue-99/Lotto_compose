package com.example.data.repository

import com.example.data.local.dao.LottoDao
import com.example.data.util.Mapper.toEntity
import com.example.domain.model.Lotto
import com.example.domain.repository.LottoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LottoRepositoryImpl @Inject constructor(
    private val lottoDao: LottoDao
): LottoRepository {
    override fun getLotto(): Flow<List<Lotto>> {
        return lottoDao.getLottoList().map { it.map { it.toDomain() } }
    }

    override suspend fun updateLotto(data: Lotto) {
        lottoDao.upsertLotto(data.toEntity())
    }

}