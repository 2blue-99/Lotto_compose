package com.example.domain.repository

import com.example.domain.model.Keyword
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getKeywordList(): Flow<List<Keyword>>
    val isRequireCameraPermission: Flow<Boolean>

    suspend fun addKeyword(title: String)

    suspend fun deleteKeyword(targetId: Int)

    suspend fun setRequireCameraPermission(state: Boolean)
}