package com.repo.trending.impl

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.repo.trending.db.RepoDao
import com.repo.trending.model.Repo
import com.repo.trending.repo.TrendingRepo

class TrendingImpl(private val noteDao: RepoDao):TrendingRepo {
    override suspend fun insert(item: Repo) = noteDao.insert(item)

    override suspend fun delete(item: Repo) = noteDao.delete(item)

    override suspend fun update(item: Repo) = noteDao.update(item)

    override fun getNotes() =
         Pager(
            config = PagingConfig(
                pageSize = 15,
                enablePlaceholders = false,
                initialLoadSize = 15
            )){
            noteDao.getNotes()
        }.flow

}