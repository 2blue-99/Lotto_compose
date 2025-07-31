package com.lucky_lotto.data.repo.di

import com.lucky_lotto.data.repo.LottoRepositoryImpl
import com.lucky_lotto.data.repo.UserRepositoryImpl
import com.lucky_lotto.domain.repository.LottoRepository
import com.lucky_lotto.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    abstract fun bindLottoRepository(lottoRepoImpl: LottoRepositoryImpl): LottoRepository

    @Binds
    abstract fun bindKeywordRepository(userRepoImpl: UserRepositoryImpl): UserRepository
}