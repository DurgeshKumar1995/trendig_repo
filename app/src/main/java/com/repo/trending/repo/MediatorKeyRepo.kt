package com.repo.trending.repo

import com.repo.trending.model.MediatorKey

interface MediatorKeyRepo {

    suspend fun insertAll(keys:List<MediatorKey>)
    suspend fun getMediatorKey(id:Long):MediatorKey

    suspend fun clearAll()

}