package com.example.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.local.dao.LottoDao
import com.example.data.local.entity.LottoEntity

@Database(
    entities = [LottoEntity::class],
    version = 1
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun lottoDao(): LottoDao
}