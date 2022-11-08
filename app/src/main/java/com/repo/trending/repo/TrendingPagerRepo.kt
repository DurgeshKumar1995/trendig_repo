package com.repo.trending.repo

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.repo.trending.db.RepoDao
import com.repo.trending.impl.TrendingPagingImpl
import com.repo.trending.model.Repo
import kotlinx.coroutines.flow.Flow


class TrendingPagerRepo(private val daoAPI: RepoDao) {

    fun getNotes(): Flow<PagingData<Repo>> {
        return Pager(
            config = PagingConfig(
                pageSize = 15,
                enablePlaceholders = false,
                initialLoadSize = 15
            ),
            pagingSourceFactory = { TrendingPagingImpl(daoAPI) }
        ).flow
    }

    suspend fun insert(item: Repo) = daoAPI.insert(item)

    suspend fun delete(item: Repo) = daoAPI.delete(item)

    suspend fun update(item: Repo) = daoAPI.update(item)

}