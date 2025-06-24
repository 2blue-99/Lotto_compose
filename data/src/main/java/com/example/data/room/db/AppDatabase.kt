package com.example.data.room.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.room.dao.LottoDao
import com.example.data.room.entity.LottoEntity

@Database(
    entities = [LottoEntity::class],
    version = 1
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun lottoDao(): LottoDao
}