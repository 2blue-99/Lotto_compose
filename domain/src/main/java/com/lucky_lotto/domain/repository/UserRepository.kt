package com.lucky_lotto.domain.repository

import com.lucky_lotto.domain.model.Keyword
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getKeywordList(): Flow<List<Keyword>>
    val isRequireCameraPermission: Flow<Boolean>
    val isFirstRandomScreen: Flow<Boolean>

    suspend fun addKeyword(title: String)

    suspend fun deleteKeyword(targetId: Int)

    suspend fun setRequireCameraPermission(state: Boolean)
    suspend fun setFirstRandomScreen(state: Boolean)

}