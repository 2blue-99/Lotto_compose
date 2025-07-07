package com.example.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.local.dao.KeywordDao
import com.example.data.local.dao.LottoRecodeDao
import com.example.data.local.dao.LottoRoundDao
import com.example.data.local.entity.KeywordEntity
import com.example.data.local.entity.LottoRoundEntity

@Database(
    entities = [LottoRoundEntity::class, KeywordEntity::class],
    version = 1
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun lottoRoundDao(): LottoRoundDao
    abstract fun lottoRecodeDao(): LottoRecodeDao
    abstract fun keywordDao(): KeywordDao
}