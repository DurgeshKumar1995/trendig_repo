package com.repo.trending.repo

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.repo.trending.impl.PagerAPIImpl
import com.repo.trending.model.Repo
import com.repo.trending.network.InterfaceGlobalAPI


class TrendAPIPagerRepo(private val globalAPI: InterfaceGlobalAPI) {

    fun getTrendRepo(roverType: String,solKey:Int): LiveData<PagingData<Repo>> {
        return Pager(
            config = PagingConfig(
                pageSize = 25,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { PagerAPIImpl(globalAPI, roverType, solKey) }
        ).liveData
    }

}