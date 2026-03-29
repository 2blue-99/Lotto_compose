package com.lucky_lotto.data.remote.datasource

import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import kotlinx.coroutines.tasks.await
import timber.log.Timber
import javax.inject.Inject

class RemoteConfigDataSourceImpl @Inject constructor(
    private val firebaseRemoteConfig: FirebaseRemoteConfig
) : RemoteConfigDataSource {

    override suspend fun fetchAndActivate(): Boolean {
        return runCatching {
            firebaseRemoteConfig.fetchAndActivate().await()
        }.onFailure {
            Timber.e(it, "RemoteConfig fetchAndActivate 실패")
        }.getOrDefault(false)
    }

    override fun getAdShowProbability(): Int =
        firebaseRemoteConfig.getLong(KEY_AD_SHOW_PROBABILITY).toInt()

    override fun getAdCooldownMinutes(): Int =
        firebaseRemoteConfig.getLong(KEY_AD_COOLDOWN_MINUTES).toInt()

    override fun getForceUpdateVersion(): String =
        firebaseRemoteConfig.getString(KEY_FORCE_UPDATE_VERSION)

    override fun getFetchNote(): String =
        firebaseRemoteConfig.getString(KEY_FETCH_NOTE)

    companion object {
        const val KEY_AD_SHOW_PROBABILITY = "ad_show_probability"
        const val KEY_AD_COOLDOWN_MINUTES = "ad_cooldown_minutes"
        const val KEY_FORCE_UPDATE_VERSION = "forced_update_version"
        const val KEY_FETCH_NOTE = "fetch_note"
    }
}
