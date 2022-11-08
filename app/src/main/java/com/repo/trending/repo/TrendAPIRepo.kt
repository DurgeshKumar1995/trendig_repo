package com.repo.trending.repo

import com.repo.trending.model.TrendRepoResponse


interface TrendAPIRepo {

    suspend fun getTrendingRepo(page:Int):TrendRepoResponse

}