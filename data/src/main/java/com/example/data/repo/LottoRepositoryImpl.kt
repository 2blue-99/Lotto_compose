package com.example.data.repo

import com.example.data.local.dao.LottoRecodeDao
import com.example.data.local.dao.LottoRoundDao
import com.example.data.remote.datasource.LottoDataSourceImpl
import com.example.data.util.Mapper.toLottoRecodeReEntity
import com.example.data.util.Utils.makeRecodeGroup
import com.example.data.util.Utils.makeStatisticItem
import com.example.data.util.toDomain
import com.example.domain.model.LottoItem
import com.example.domain.model.LottoRecode
import com.example.domain.model.LottoRound
import com.example.domain.model.StatisticItem
import com.example.domain.repository.LottoRepository
import com.example.domain.type.RangeType
import com.example.domain.util.CommonUtils.currentDateTimeString
import com.example.domain.util.CommonUtils.getPastDate
import com.example.domain.util.ResourceState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
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
        val currentTime = currentDateTimeString()
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



    override suspend fun getRangeStatistic(range: RangeType): List<StatisticItem> {
        // 해당 범위의 로또 정보 가져오기
        val targetMonth = getPastDate(range.month)

        // 범위에 해당하는 로또 회차 리스트
        val roundRangeList = lottoRoundDao.getRangeLottoRoundDao(targetMonth)

        // 가공해서 List<StatisticItem> 로 만들기
        return roundRangeList.makeStatisticItem()
    }
}