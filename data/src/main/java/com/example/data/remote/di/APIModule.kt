package com.example.data.remote.di

import com.example.data.remote.datasource.LottoDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object APIModule {
    @Singleton
    @Provides
    fun provideLottoRetrofit(retrofit: Retrofit): LottoDataSource =
        retrofit.create(LottoDataSource::class.java)
}