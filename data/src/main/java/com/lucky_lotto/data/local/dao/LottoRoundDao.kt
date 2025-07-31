package com.lucky_lotto.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lucky_lotto.data.local.entity.LottoRoundEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LottoRoundDao {
    @Query("Select * From lotto_round")
    fun getLottoRoundDao(): Flow<List<LottoRoundEntity>>

    @Query("Select * From lotto_round Where drawDate >= :date")
    suspend fun getRangeLottoRoundDao(date: String): List<LottoRoundEntity>

    // 1부터 빈틈없이 쌓이기 때문에 DB 사이즈로 판단하면 됨
    @Query("Select Count(*) from lotto_round")
    suspend fun getLastDrawNumber(): Int



    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertLotto(lottoList: List<LottoRoundEntity>)
}