package com.repo.trending.impl


import com.repo.trending.network.InterfaceGlobalAPI
import com.repo.trending.repo.TrendAPIRepo


class TrendAPIImpl(private val globalAPI: InterfaceGlobalAPI): TrendAPIRepo {
    override suspend fun getTrendingRepo(page: Int) = globalAPI.getTrendingRepo(page)


}