package com.example.domain.repository

import com.example.domain.model.Keyword
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getKeywordList(): Flow<List<Keyword>>

    suspend fun addKeyword(title: String)

    suspend fun deleteKeyword(targetId: Int)
}