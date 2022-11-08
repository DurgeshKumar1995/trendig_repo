package com.repo.trending.repo

import com.repo.trending.model.TrendRepoResponse
import com.repo.trending.network.Resource


interface TrendAPIRepo {

    suspend fun getTrendingRepo(page:Int): Resource<TrendRepoResponse>

}