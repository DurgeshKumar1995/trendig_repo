package com.repo.trending.impl


import com.repo.trending.network.InterfaceGlobalAPI
import com.repo.trending.repo.TrendRESTAPIRepo


class TrendRESTAPIImpl(private val globalAPI: InterfaceGlobalAPI): TrendRESTAPIRepo {
    override suspend fun getTrendingRepo(page: Int) = globalAPI.getTrendingRepo(page)

}