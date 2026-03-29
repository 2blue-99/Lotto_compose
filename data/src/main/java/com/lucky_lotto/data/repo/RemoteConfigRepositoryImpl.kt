package com.lucky_lotto.data.repo

import android.content.Context
import com.lucky_lotto.data.remote.datasource.RemoteConfigDataSource
import com.lucky_lotto.data.util.Utils.isUpdateRequired
import com.lucky_lotto.domain.model.RemoteConfig
import com.lucky_lotto.domain.repository.RemoteConfigRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteConfigRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val remoteConfigDataSource: RemoteConfigDataSource
) : RemoteConfigRepository {

    override suspend fun fetchAndActivate(): Result<RemoteConfig> {
        return runCatching {
            remoteConfigDataSource.fetchAndActivate()
            getConfig()
        }
    }

    override fun getConfig(): RemoteConfig {
        val appVersion = context.packageManager.getPackageInfo(context.packageName, 0).versionName ?: ""
        val forceVersion = remoteConfigDataSource.getForceUpdateVersion()
        val isUpdateRequired = isUpdateRequired(appVersion, forceVersion)
        return RemoteConfig(
            adShowProbability = remoteConfigDataSource.getAdShowProbability(),
            adCooldownMinutes = remoteConfigDataSource.getAdCooldownMinutes(),
            isRequestUpdate = isUpdateRequired,
            fetchNote = remoteConfigDataSource.getFetchNote(),
        )
    }
}
