package com.repo.trending.network

import com.repo.trending.model.Repo
import com.repo.trending.model.TrendRepoResponse
import retrofit2.http.*


interface InterfaceGlobalAPI {

    companion object{
        private const val QUERY_KEY = "q"
        private const val QUERY_VALUE = "tranding"
        private const val ORDER_KEY = "order"
        private const val ORDER_VALUE = "desc"
        private const val PAGE_KEY = "page"
        private const val SORT_KEY = "sort"
        private const val SORT_VALUE = "stars"
    }

    @GET("search/repositories")
    suspend fun getTrendingRepo(
        @Path(PAGE_KEY) page: Int,
        @Path(QUERY_KEY) queryKey: String= QUERY_VALUE,
        @Query(ORDER_KEY) order:String=ORDER_VALUE,
        @Query(SORT_KEY) sort:String=SORT_VALUE
    ): TrendRepoResponse



}