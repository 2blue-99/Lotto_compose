package com.example.data.datastore

import kotlinx.coroutines.flow.Flow

interface UserDataStore {
    val isRequireCameraPermission: Flow<Boolean>

    suspend fun setRequireCameraPermission(state: Boolean)
}