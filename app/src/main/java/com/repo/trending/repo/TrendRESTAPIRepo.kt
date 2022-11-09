package com.repo.trending.repo

import com.repo.trending.ui.common_model.TrendRepoResponse


interface TrendRESTAPIRepo {

    suspend fun getTrendingRepo(page:Int): TrendRepoResponse

}