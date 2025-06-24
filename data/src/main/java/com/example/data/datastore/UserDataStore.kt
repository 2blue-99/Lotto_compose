package com.example.data.datastore

import kotlinx.coroutines.flow.Flow

interface UserDataStore {
    val userNameFlow: Flow<String>

    suspend fun setUserName(name: String)
}