package com.example.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.local.entity.KeywordEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface KeywordDao {
    @Query("Select * From keyword")
    fun getKeywordList(): Flow<List<KeywordEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertKeyword(lotto: KeywordEntity)

    @Query("Delete From keyword Where id = :targetId")
    suspend fun deleteKeyword(targetId: Int)
}