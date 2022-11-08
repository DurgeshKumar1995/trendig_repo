package com.repo.trending.impl

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.repo.trending.model.Repo
import com.repo.trending.network.InterfaceGlobalAPI

class PagerAPIImpl(private val globalAPI: InterfaceGlobalAPI, private val roverType: String, private val solKey:Int) : PagingSource<Int, Repo>(){
    override fun getRefreshKey(state: PagingState<Int, Repo>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Repo> {
        val position = params.key ?: 1
        return try {
            val response = globalAPI.getPhotos(roverType, solKey,position)
            val items = response
            val nextKey = if (items.isNullOrEmpty() || items.size<25) {
                null
            } else {
                position + 1
            }
            LoadResult.Page(
                data = items?: emptyList(),
                prevKey = if (position == 1) null else position - 1,
                nextKey = nextKey
            )
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }


}