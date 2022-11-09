package com.repo.trending.db.mediator

import android.content.SharedPreferences
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.repo.trending.db.RepoDatabase
import com.repo.trending.di.SharedPrefrence
import com.repo.trending.model.MediatorKey
import com.repo.trending.model.Repo
import com.repo.trending.repo.MediatorKeyRepo
import com.repo.trending.repo.TrendRESTAPIRepo
import com.repo.trending.repo.TrendingDBRepo
import com.repo.trending.utils.Constants
import com.repo.trending.utils.DateUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import java.util.Date

@OptIn(ExperimentalPagingApi::class)
class RepoMediator(
    private val mediatorKeyRepo: MediatorKeyRepo,
    private val trendingRepo: TrendingDBRepo,
    private val trendAPIRepo: TrendRESTAPIRepo,
    private val repoDatabase: RepoDatabase,
    private val sharedPreferences: SharedPreferences
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
            val response = trendAPIRepo.getTrendingRepo(page)

            val isLastPage =
                response.items.isEmpty() || (response.items.size < Constants.REPO_PAGE_SIZE)
            repoDatabase.withTransaction {
                val dateStringFromShared = sharedPreferences.getString(SharedPrefrence.shredPreferencesDateKey,null)
                val dateCurrent = Date()
                dateCurrent.time = System.currentTimeMillis()
                val currentDate = DateUtils.getDate(dateCurrent)
                if (loadType == LoadType.REFRESH &&  dateStringFromShared!=currentDate) {
                    mediatorKeyRepo.clearAll()
                    trendingRepo.clearAll()
                }
                sharedPreferences.edit().putString(SharedPrefrence.shredPreferencesDateKey,currentDate).apply()
                val prevKey = if (page == 1) null else page.minus(1)
                val nextKey = if (isLastPage) null else page.plus(1)
                val remoteKeys = response.items.map { repo ->
                    MediatorKey(repo.id, prevKey = prevKey, nextKey = nextKey)
                }
                try {
                    trendingRepo.insertAll(response.items)
                }catch (ignored:Exception){}
                try {
                mediatorKeyRepo.insertAll(remoteKeys)
                }catch (ignored:Exception){}
                MediatorResult.Success(isLastPage)
            }

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
            LoadType.PREPEND -> {
                return MediatorResult.Success(true)
            }
            LoadType.APPEND -> {
                getAppendData(state)?.nextKey
                    ?: MediatorResult.Success(endOfPaginationReached = true)
            }
        }
    }

    private suspend fun getRefreshData(state: PagingState<Int, Repo>): MediatorKey? {

        return withContext(Dispatchers.IO) {

            state.anchorPosition?.let { position ->
                state.closestItemToPosition(position)?.id?.let { id ->
                    mediatorKeyRepo.getMediatorKey(id)
                }
            }
        }

    }

    private suspend fun getAppendData(state: PagingState<Int, Repo>): MediatorKey? {
        return withContext(Dispatchers.IO) {

             state.lastItemOrNull()?.let { repo ->
                 mediatorKeyRepo.getMediatorKey(repo.id)
            }
        }
    }

}