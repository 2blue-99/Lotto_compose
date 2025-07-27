package com.example.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserStoreImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
): UserDataStore {
    object PreferencesKey {
        val REQUIRE_CAMERA_PERMISSION = booleanPreferencesKey("require_camera_permission")
    }

    override val isRequireCameraPermission: Flow<Boolean> =
        dataStore.data.map { dataStore -> dataStore[PreferencesKey.REQUIRE_CAMERA_PERMISSION] ?: false }

    override suspend fun setRequireCameraPermission(state: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.REQUIRE_CAMERA_PERMISSION] = state
        }
    }
}