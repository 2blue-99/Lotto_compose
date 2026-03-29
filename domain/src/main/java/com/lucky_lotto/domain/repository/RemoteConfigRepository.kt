package com.lucky_lotto.domain.repository

import com.lucky_lotto.domain.model.RemoteConfig

interface RemoteConfigRepository {
    suspend fun fetchAndActivate(): Result<RemoteConfig>
    fun getConfig(): RemoteConfig
}
