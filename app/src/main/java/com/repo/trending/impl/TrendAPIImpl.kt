package com.repo.trending.impl


import com.repo.trending.network.BaseRepository
import com.repo.trending.network.InterfaceGlobalAPI
import com.repo.trending.repo.TrendAPIRepo


class TrendAPIImpl(private val globalAPI: InterfaceGlobalAPI,private val baseRepository: BaseRepository): TrendAPIRepo {
    override suspend fun getTrendingRepo(page: Int) = baseRepository.safeApiCall {
        globalAPI.getTrendingRepo(page)
    }


}