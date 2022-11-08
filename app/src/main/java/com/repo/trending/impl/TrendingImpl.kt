package com.repo.trending.impl

import androidx.paging.*
import com.repo.trending.db.RepoDatabase
import com.repo.trending.db.dao.RepoDao
import com.repo.trending.db.mediator.RepoMediator
import com.repo.trending.model.Repo
import com.repo.trending.repo.TrendingRepo
import kotlinx.coroutines.flow.Flow

class TrendingImpl(private val repoDao: RepoDao):TrendingRepo {
    override suspend fun insert(item: Repo) = repoDao.insert(item)

    override suspend fun delete(item: Repo) = repoDao.delete(item)

    override suspend fun update(item: Repo) = repoDao.update(item)
    override fun getRepos(): PagingSource<Int, Repo> = repoDao.getRepos()


    override suspend fun insertAll(items: List<Repo>) = repoDao.insertAll(items)

    override suspend fun clearAll()= repoDao.clearAll()
    override suspend fun getRepoByFilterString(search: String)= repoDao.getRepoByFilterString(search)

}