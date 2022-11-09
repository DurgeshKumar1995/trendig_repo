package com.repo.trending.repo

import com.repo.trending.model.TrendRepoResponse


interface TrendRESTAPIRepo {

    suspend fun getTrendingRepo(page:Int): TrendRepoResponse

}