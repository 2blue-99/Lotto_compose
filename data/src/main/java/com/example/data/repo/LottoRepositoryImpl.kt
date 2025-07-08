package com.example.data.repo

import com.example.data.local.dao.LottoRecodeDao
import com.example.data.local.dao.LottoRoundDao
import com.example.data.remote.datasource.LottoDataSourceImpl
import com.example.data.util.Mapper.toLottoRecodeReEntity
import com.example.data.util.Utils.makeRecodeGroup
import com.example.data.util.toDomain
import com.example.domain.model.LottoItem
import com.example.domain.model.LottoRecode
import com.example.domain.model.LottoRound
import com.example.domain.repository.LottoRepository
import com.example.domain.util.CommonUtils.currentTimeString
import com.example.domain.util.ResourceState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LottoRepositoryImpl @Inject constructor(
    private val lottoRoundDao: LottoRoundDao,
    private val lottoRecodeDao: LottoRecodeDao,
    private val lottoDataSource: LottoDataSourceImpl
): LottoRepository {
    override fun getLottoRoundDao(): Flow<List<LottoRound>> {
        return lottoRoundDao.getLottoRoundDao().map { it.map { it.toDomain() } }
    }

    override fun getLottoRecodeDao(): Flow<List<LottoRecode>> {
        return lottoRecodeDao.getLottoRecodeDao().map { it.makeRecodeGroup() }
    }



    override suspend fun insertLottoRecodeDao(list:List<LottoItem>) {
        val currentTime = currentTimeString()
        return lottoRecodeDao.upsertRecodeList(list.map { it.toLottoRecodeReEntity(currentTime) })
    }

    override suspend fun deleteLottoRecodeDao(date: String) {
        lottoRecodeDao.deleteRecodeList(date)
    }

//    override suspend fun updateLotto(data: LottoRecode) {
//        lottoDao.upsertLotto(data.toEntity())
//    }

    override suspend fun requestLottoData(round: Int): ResourceState<LottoRound> {
        return lottoDataSource.requestLottoData(round.toString()).toDomain { it.toDomain() }
    }
}