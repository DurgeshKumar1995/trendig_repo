package com.repo.trending.impl

import com.repo.trending.db.dao.RemoteMediatorRepoDao
import com.repo.trending.model.MediatorKey
import com.repo.trending.repo.MediatorKeyRepo

class MediatorKeyImpl(private val repoDao: RemoteMediatorRepoDao) : MediatorKeyRepo{
    override suspend fun insertAll(keys: List<MediatorKey>) = repoDao.insertAll(keys)

    override suspend fun getMediatorKey(id: Long)= repoDao.getMediatorKey(id)
    override suspend fun clearAll() = repoDao.clearAll()
}