package com.repo.trending.db.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.repo.trending.db.RepoDatabase
import com.repo.trending.model.MediatorKey
import com.repo.trending.model.Repo
import com.repo.trending.repo.MediatorKeyRepo
import com.repo.trending.repo.TrendAPIRepo
import com.repo.trending.repo.TrendingRepo
import com.repo.trending.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalPagingApi::class)
class RepoMediator(
    private val mediatorKeyRepo: MediatorKeyRepo,
    private val trendingRepo: TrendingRepo,
    private val trendAPIRepo: TrendAPIRepo,
    private val repoDatabase: RepoDatabase
) : RemoteMediator<Int, Repo>() {

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(loadType: LoadType, state: PagingState<Int, Repo>): MediatorResult {
        val page = when (val pageData = getPageData(loadType, state)) {
            is MediatorResult.Success -> return pageData
            else -> pageData as Int
        }
        return try {
            val response = trendAPIRepo.getTrendingRepo(page = page)
            val isLastPage =
                response.items.isEmpty() || response.items.size < Constants.REPO_PAGE_SIZE
            repoDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    mediatorKeyRepo.clearAll()
                    trendingRepo.clearAll()
                }
                val prevKey = if (page == 1) null else page.minus(1)
                val nextKey = if (isLastPage) null else page.plus(1)
                val remoteKeys = response.items.map { repo ->
                    MediatorKey(repo.id, prevKey = prevKey, nextKey = nextKey)
                }
                trendingRepo.insertAll(response.items)
                mediatorKeyRepo.insertAll(remoteKeys)
            }
            MediatorResult.Success(isLastPage)
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }

    }


    private suspend fun getPageData(loadType: LoadType, state: PagingState<Int, Repo>): Any {
        return when (loadType) {
            LoadType.REFRESH -> {
                getRefreshData(state)?.nextKey?.minus(1) ?: 1
            }
            LoadType.APPEND -> {
                getPrependData(state)?.prevKey
                    ?: MediatorResult.Success(endOfPaginationReached = false)
            }
            LoadType.PREPEND -> {
                getAppendData(state)?.nextKey
                    ?: MediatorResult.Success(endOfPaginationReached = true)
            }
        }
    }

    private suspend fun getRefreshData(state: PagingState<Int, Repo>): MediatorKey? {

        return withContext(Dispatchers.IO) {
            state.anchorPosition?.let { position ->
                state.closestItemToPosition(position)?.id?.let {
                    mediatorKeyRepo.getMediatorKey(it)
                }
            }
        }

    }

    private suspend fun getAppendData(state: PagingState<Int, Repo>): MediatorKey? {
        return withContext(Dispatchers.IO) {
            state.pages.lastOrNull {
                it.data.isNotEmpty()
            }?.data?.lastOrNull()?.let { repo ->
                mediatorKeyRepo.getMediatorKey(repo.id)
            }
        }
    }

    private suspend fun getPrependData(state: PagingState<Int, Repo>): MediatorKey? {

        return withContext(Dispatchers.IO) {
//            state.pages.first().data.first().let {
//                mediatorKeyRepo.getMediatorKey(it.id)
//            }
            state.pages.firstOrNull {
                it.data.isNotEmpty()
            }?.data?.firstOrNull()?.let { repo ->
                mediatorKeyRepo.getMediatorKey(repo.id)
            }
        }
    }
}