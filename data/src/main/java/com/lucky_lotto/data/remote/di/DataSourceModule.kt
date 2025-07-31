package com.lucky_lotto.data.remote.di

import com.lucky_lotto.data.remote.datasource.LottoDataSource
import com.lucky_lotto.data.remote.datasource.LottoDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {
    @Provides
    @Singleton
    fun provideLottoDataSource(
        dataSource: LottoDataSource
    ): LottoDataSourceImpl = LottoDataSourceImpl(dataSource)
}