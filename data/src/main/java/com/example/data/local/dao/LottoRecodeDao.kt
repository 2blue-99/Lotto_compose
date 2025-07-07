package com.example.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.local.entity.LottoRecodeEntity
import com.example.data.local.entity.LottoRoundEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LottoRecodeDao {
    @Query("Select * From lotto")
    fun getLottoDao(): Flow<List<LottoRecodeEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertRecodeList(lottoList: List<LottoRecodeEntity>)
}