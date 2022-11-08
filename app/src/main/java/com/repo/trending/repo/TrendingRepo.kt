package com.repo.trending.repo

import androidx.paging.PagingData
import com.repo.trending.model.Repo
import kotlinx.coroutines.flow.Flow

interface TrendingRepo {

    suspend fun insert(item: Repo): Long
    suspend fun delete(item: Repo): Int
    suspend fun update(item: Repo): Int
    fun getNotes(): Flow<PagingData<Repo>>
}