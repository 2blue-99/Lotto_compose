package com.example.data.repo

import com.example.data.local.dao.LottoDao
import com.example.data.remote.datasource.LottoDataSourceImpl
import com.example.data.util.Mapper.toEntity
import com.example.data.util.toDomain
import com.example.domain.model.Lotto
import com.example.domain.model.LottoRecode
import com.example.domain.repository.LottoRepository
import com.example.domain.util.ResourceState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LottoRepositoryImpl @Inject constructor(
    private val lottoDao: LottoDao,
    private val lottoDataSource: LottoDataSourceImpl
): LottoRepository {
    override fun getLottoDao(): Flow<List<LottoRecode>> {
        return lottoDao.getLottoDao().map { it.map { it.toDomain() } }
    }

    override suspend fun updateLotto(data: LottoRecode) {
        lottoDao.upsertLotto(data.toEntity())
    }

    override suspend fun getLottoData(round: Int): ResourceState<Lotto> {
        return lottoDataSource.requestLottoData(round.toString()).toDomain { it.toDomain() }
    }
}