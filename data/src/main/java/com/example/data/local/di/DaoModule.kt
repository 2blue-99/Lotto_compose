package com.example.data.local.di

import com.example.data.local.dao.KeywordDao
import com.example.data.local.dao.LottoDao
import com.example.data.local.db.AppDatabase
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

    @Singleton
    @Provides
    fun provideKeywordDao(database: AppDatabase): KeywordDao =
        database.keywordDao()
}