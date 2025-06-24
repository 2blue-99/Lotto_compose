package com.example.data.repo

import com.example.data.local.dao.LottoDao
import com.example.data.remote.datasource.LottoDataSource
import com.example.data.remote.datasource.LottoDataSourceImpl
import com.example.data.util.Mapper.toEntity
import com.example.domain.model.Lotto
import com.example.domain.repository.LottoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import timber.log.Timber
import javax.inject.Inject

class LottoRepositoryImpl @Inject constructor(
    private val lottoDao: LottoDao,
    private val lottoDataSource: LottoDataSourceImpl
): LottoRepository {
    override fun getLotto(): Flow<List<Lotto>> {
        return lottoDao.getLottoList().map { it.map { it.toDomain() } }
    }

    override suspend fun updateLotto(data: Lotto) {
        lottoDao.upsertLotto(data.toEntity())
    }

    override suspend fun requestLotto() {
        Timber.d("requestLotto")
        val result = lottoDataSource.requestLottoData("1177")
        Timber.d("result : $result")
    }
}