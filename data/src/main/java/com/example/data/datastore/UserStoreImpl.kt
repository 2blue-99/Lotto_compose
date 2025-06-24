package com.example.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserStoreImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
): UserDataStore {
    object PreferencesKey {
        val TEST = stringPreferencesKey("user_name")
    }

    override val userNameFlow: Flow<String> =
        dataStore.data.map { dataStore -> dataStore[PreferencesKey.TEST] ?: "디폴트" }

    override suspend fun setUserName(name: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.TEST] = name
        }
    }
}