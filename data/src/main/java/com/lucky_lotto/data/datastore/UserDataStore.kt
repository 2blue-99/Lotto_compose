package com.lucky_lotto.data.datastore

import kotlinx.coroutines.flow.Flow

interface UserDataStore {
    val isRequireCameraPermission: Flow<Boolean>
    val isFirstRandomScreen: Flow<Boolean>

    suspend fun setRequireCameraPermission(state: Boolean)
    suspend fun setFirstRandomScreen(state: Boolean)
}