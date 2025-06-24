package com.example.data.repository.di

import com.example.data.local.dao.LottoDao
import com.example.data.repository.LottoRepositoryImpl
import com.example.domain.repository.LottoRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    abstract fun bindLottoRepository(lottoRepoImpl: LottoRepositoryImpl): LottoRepository
}