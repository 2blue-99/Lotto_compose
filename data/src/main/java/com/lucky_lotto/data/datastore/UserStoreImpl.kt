package com.lucky_lotto.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserStoreImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
): UserDataStore {
    object PreferencesKey {
        val REQUIRE_CAMERA_PERMISSION = booleanPreferencesKey("require_camera_permission")
        // 행운 추첨 타이틀 최초 1회 확장을 위한 데이터
        val FIRST_RANDOM_SCREEN = booleanPreferencesKey("first_random_screen")
    }

    override val isRequireCameraPermission: Flow<Boolean> =
        dataStore.data.map { dataStore -> dataStore[PreferencesKey.REQUIRE_CAMERA_PERMISSION] ?: false }

    override val isFirstRandomScreen: Flow<Boolean> =
        dataStore.data.map { dataStore -> dataStore[PreferencesKey.FIRST_RANDOM_SCREEN] ?: true }




    override suspend fun setRequireCameraPermission(state: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.REQUIRE_CAMERA_PERMISSION] = state
        }
    }

    override suspend fun setFirstRandomScreen(state: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.FIRST_RANDOM_SCREEN] = state
        }
    }
}