package com.lucky_lotto.domain.repository

import com.lucky_lotto.domain.model.LottoItem
import com.lucky_lotto.domain.model.LottoRecode
import com.lucky_lotto.domain.model.LottoRound
import com.lucky_lotto.domain.model.StatisticItem
import com.lucky_lotto.domain.type.RangeType
import kotlinx.coroutines.flow.Flow

interface LottoRepository {
    /**
     * 로또 회차 정보
     */
    fun getLottoRoundDao(): Flow<List<LottoRound>>
    /**
     * 범위 기준 로또 회차 정보 -> 통계 데이터 변환
     */
    suspend fun getRangeStatistic(range: RangeType): List<StatisticItem>

    /**
     * 로또 회차 정보 업데이트 - API 조회
     */
    suspend fun updateLottoRound(): Boolean


    /**
     * 저장 추천 리스트 조회
     */
    fun getLottoRecodeDao(): Flow<List<LottoRecode>>
    /**
     * 추첨 리스트 저장
     *
     * @param drawType : 추첨 방식
     * @param drawData : 추첨 활용 정보 (키워드 or 필수 번호)
     * @param drawData : 추첨 번호 리스트
     */
    suspend fun insertLottoRecodeDao(drawType: String, drawData: String, list:List<LottoItem>)

    /**
     * 로또 추첨 결과 삭제
     */
    suspend fun deleteLottoRecodeDao(date: String)
}