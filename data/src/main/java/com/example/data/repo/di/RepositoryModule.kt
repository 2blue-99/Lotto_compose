package com.example.data.repo.di

import com.example.data.repo.LottoRepositoryImpl
import com.example.domain.repository.LottoRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    abstract fun bindLottoRepository(lottoRepoImpl: LottoRepositoryImpl): LottoRepository
}