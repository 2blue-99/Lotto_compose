package com.example.data.room.di

import com.example.data.room.dao.LottoDao
import com.example.data.room.db.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {
    @Singleton
    @Provides
    fun provideLottoDao(database: AppDatabase): LottoDao =
        database.lottoDao()
}