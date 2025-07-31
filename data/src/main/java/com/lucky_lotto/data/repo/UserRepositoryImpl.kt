package com.lucky_lotto.data.repo

import com.lucky_lotto.data.datastore.UserDataStore
import com.lucky_lotto.data.local.dao.KeywordDao
import com.lucky_lotto.data.local.entity.KeywordEntity
import com.lucky_lotto.domain.model.Keyword
import com.lucky_lotto.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val keywordDao: KeywordDao,
    private val userDataStore: UserDataStore
): UserRepository {
    override fun getKeywordList(): Flow<List<Keyword>> {
        return keywordDao.getKeywordList().map { it.map { it.toDomain() } }
    }

    override val isRequireCameraPermission: Flow<Boolean> =
        userDataStore.isRequireCameraPermission

    override val isFirstRandomScreen: Flow<Boolean> =
        userDataStore.isFirstRandomScreen




    override suspend fun addKeyword(title: String) {
        return keywordDao.upsertKeyword(KeywordEntity(id =  0, title = title))
    }

    override suspend fun deleteKeyword(targetId: Int) {
        return keywordDao.deleteKeyword(targetId)
    }

    override suspend fun setRequireCameraPermission(state: Boolean) {
        return userDataStore.setRequireCameraPermission(state)
    }

    override suspend fun setFirstRandomScreen(state: Boolean) {
        return userDataStore.setFirstRandomScreen(state)
    }
}