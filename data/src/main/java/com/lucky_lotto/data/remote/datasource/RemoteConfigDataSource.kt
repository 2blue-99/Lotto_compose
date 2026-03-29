package com.lucky_lotto.data.remote.datasource

interface RemoteConfigDataSource {
    suspend fun fetchAndActivate(): Boolean
    fun getAdShowProbability(): Int
    fun getAdCooldownMinutes(): Int
    fun getForceUpdateVersion(): String
    fun getFetchNote(): String
}
