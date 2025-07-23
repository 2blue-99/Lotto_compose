package com.example.domain.repository

import com.example.domain.model.LottoItem
import com.example.domain.model.LottoRecode
import com.example.domain.model.LottoRound
import com.example.domain.model.StatisticItem
import com.example.domain.type.RangeType
import com.example.domain.util.ResourceState
import kotlinx.coroutines.flow.Flow

interface LottoRepository {
    /**
     * 로또 회차 정보
     */
    fun getLottoRoundDao(): Flow<List<LottoRound>>
    /**
     * 로또 범위 회차 정보 -> 통계 데이터
     */
    suspend fun getRangeStatistic(range: RangeType): List<StatisticItem>



    /**
     * 로또 추첨 결과 조회
     */
    fun getLottoRecodeDao(): Flow<List<LottoRecode>>
    /**
     * 로또 추첨 결과 저장
     */
    suspend fun insertLottoRecodeDao(list:List<LottoItem>)

    /**
     * 로또 추첨 결과 저장
     */
    suspend fun deleteLottoRecodeDao(date: String)



    /**
     * 로또 API 조회
     */
    suspend fun requestLottoData(round: Int): ResourceState<LottoRound>
}