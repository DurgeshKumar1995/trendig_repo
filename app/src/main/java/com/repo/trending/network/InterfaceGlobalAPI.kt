package com.repo.trending.network

import com.repo.trending.model.Repo
import com.repo.trending.utils.Constants
import retrofit2.http.*


interface InterfaceGlobalAPI {

    companion object{
        private const val API_KEY = "api_key"
        private const val SOL_KEY = "sol"
        private const val PAGE_KEY = "page"
        private const val ROVERS_TYPE_KEY = "roverType"
    }

    @GET("{roverType}/photos")
    suspend fun getPhotos(
        @Path(ROVERS_TYPE_KEY) roverType: String,
        @Query(SOL_KEY) solKey:Int,
        @Query(PAGE_KEY) pageId:Int=1,
        @Query(API_KEY) apiKey:String= Constants.REPO_SHARE_KEY
    ): List<Repo>



}