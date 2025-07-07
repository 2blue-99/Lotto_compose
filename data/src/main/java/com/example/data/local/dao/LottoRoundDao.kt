package com.example.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.local.entity.LottoRoundEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LottoRoundDao {
    @Query("Select * From lotto")
    fun getLottoDao(): Flow<List<LottoRoundEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertLotto(lottoList: LottoRoundEntity)
}