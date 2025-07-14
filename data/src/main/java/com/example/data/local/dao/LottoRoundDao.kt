package com.example.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.local.entity.LottoRoundEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LottoRoundDao {
    @Query("Select * From lotto_round")
    fun getLottoRoundDao(): Flow<List<LottoRoundEntity>>

    @Query("Select * From lotto_round Where drawDate >= :date")
    suspend fun getRangeLottoRoundDao(date: String): List<LottoRoundEntity>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertLotto(lottoList: LottoRoundEntity)
}