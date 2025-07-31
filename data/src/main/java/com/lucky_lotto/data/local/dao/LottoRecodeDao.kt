package com.lucky_lotto.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lucky_lotto.data.local.entity.LottoRecodeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LottoRecodeDao {
    @Query("Select * From lotto_recode")
    fun getLottoRecodeDao(): Flow<List<LottoRecodeEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertRecodeList(lottoList: List<LottoRecodeEntity>)

    @Query("Delete From lotto_recode Where saveDate = :date")
    suspend fun deleteRecodeList(date: String)
}