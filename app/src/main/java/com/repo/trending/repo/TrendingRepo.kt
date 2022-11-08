package com.repo.trending.repo

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import com.repo.trending.model.Repo
import kotlinx.coroutines.flow.Flow

interface TrendingRepo {

    suspend fun insert(item: Repo): Long
    suspend fun delete(item: Repo): Int
    suspend fun update(item: Repo): Int
    fun getRepos(): PagingSource<Int, Repo>
    suspend fun insertAll(items: List<Repo>)
    suspend fun clearAll()

    suspend fun getRepoByFilterString(search:String): List<Repo>
}