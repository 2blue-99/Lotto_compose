package com.example.data.repo

import com.example.data.local.dao.LottoRecodeDao
import com.example.data.local.dao.LottoRoundDao
import com.example.data.local.entity.LottoRoundEntity
import com.example.data.remote.datasource.LottoDataSourceImpl
import com.example.data.util.Mapper.toLottoRecodeReEntity
import com.example.data.util.Utils.getWeekCountBetweenTargetDate
import com.example.data.util.Utils.makeRecodeGroup
import com.example.data.util.Utils.makeStatisticItem
import com.example.domain.model.LottoItem
import com.example.domain.model.LottoRecode
import com.example.domain.model.LottoRound
import com.example.domain.model.StatisticItem
import com.example.domain.repository.LottoRepository
import com.example.domain.type.RangeType
import com.example.domain.util.APIResponseState
import com.example.domain.util.CommonUtils.currentDateTimeString
import com.example.domain.util.CommonUtils.getPastDate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import timber.log.Timber
import javax.inject.Inject

class LottoRepositoryImpl @Inject constructor(
    private val lottoRoundDao: LottoRoundDao,
    private val lottoRecodeDao: LottoRecodeDao,
    private val lottoDataSource: LottoDataSourceImpl
): LottoRepository {

    override fun getLottoRoundDao(): Flow<List<LottoRound>> {
        return lottoRoundDao.getLottoRoundDao().map { it.map { it.toDomain() } }
    }

    override suspend fun getRangeStatistic(range: RangeType): List<StatisticItem> {
        // 해당 범위의 로또 정보 가져오기
        val targetMonth = getPastDate(range.monthValue)

        // 범위에 해당하는 로또 회차 리스트
        val roundRangeList = lottoRoundDao.getRangeLottoRoundDao(targetMonth)

        // 가공해서 List<StatisticItem> 로 만들기
        return roundRangeList.makeStatisticItem()
    }

    /**
     * 로또 회차 정보 업데이트
     *
     * @return True : 업데이트 정상 완료
     * @return False : 업데이트할 필요 없음
     */
    override suspend fun updateLottoRound(): Boolean {
        // 로컬 가장 최근 추첨 회차
        val localLatestRound = lottoRoundDao.getLastDrawNumber()
        Timber.d("localLatestRound : $localLatestRound")

        val differenceWeek = getWeekCountBetweenTargetDate()
        // 실제 최근 추첨 회차 예상치
        val targetLatestRound = differenceWeek + 1 // 추첨일 기준, 1회차부터 시작했기 때문
        Timber.d("remoteLatestRound : $targetLatestRound")

        // 최신 상태이므로 return
        if(localLatestRound == targetLatestRound){
            return true
        }

        // 로컬과 최신 데이터 회차 차이 계산
        val targetRounds = (localLatestRound+1..targetLatestRound).toList()
        Timber.d("targetRounds : $targetRounds")

        // API 응답 리스트
        val resultList = mutableListOf<LottoRoundEntity>()
        try {
            targetRounds.forEach {
                val response = lottoDataSource.requestLottoData(it.toString())
                when(response){
                    is APIResponseState.Success -> {
                        // 변환
                        val roundData = response.body.toLottoRoundEntity()
                        // 리스트에 저장
                        resultList.add(roundData)
                    }
                    else -> {
                        throw Exception("Err : $response")
                    }
                }
            }
        } catch (e: Exception){
            throw Exception("Err : $e")
        } finally {
            // 회차 정보 저장
            if(resultList.isNotEmpty()){
                lottoRoundDao.upsertLotto(resultList)
                Timber.d("resultList : $resultList")
            }
        }
        return true
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
}