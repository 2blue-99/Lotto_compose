package com.lucky_lotto.data.remote.di

import com.lucky_lotto.data.remote.datasource.LottoDataSource
import com.lucky_lotto.data.remote.datasource.LottoDataSourceImpl
import com.lucky_lotto.data.remote.datasource.RemoteConfigDataSource
import com.lucky_lotto.data.remote.datasource.RemoteConfigDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    @Singleton
    abstract fun bindRemoteConfigDataSource(
        impl: RemoteConfigDataSourceImpl
    ): RemoteConfigDataSource

    companion object {
        @Provides
        @Singleton
        fun provideLottoDataSource(
            dataSource: LottoDataSource
        ): LottoDataSourceImpl = LottoDataSourceImpl(dataSource)
    }
}