package com.example.data.repo

import com.example.data.datastore.UserDataStore
import com.example.data.local.dao.KeywordDao
import com.example.data.local.entity.KeywordEntity
import com.example.domain.model.Keyword
import com.example.domain.repository.UserRepository
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



    override suspend fun addKeyword(title: String) {
        return keywordDao.upsertKeyword(KeywordEntity(id =  0, title = title))
    }

    override suspend fun deleteKeyword(targetId: Int) {
        return keywordDao.deleteKeyword(targetId)
    }

    override suspend fun setRequireCameraPermission(state: Boolean) {
        return userDataStore.setRequireCameraPermission(state)
    }
}