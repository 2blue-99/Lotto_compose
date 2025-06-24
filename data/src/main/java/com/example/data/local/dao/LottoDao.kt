package com.example.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.local.entity.LottoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LottoDao {
    @Query("Select * From lotto")
    fun getLottoList(): Flow<List<LottoEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertLotto(lotto: LottoEntity)
}