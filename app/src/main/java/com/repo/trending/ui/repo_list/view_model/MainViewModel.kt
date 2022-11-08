package com.repo.trending.ui.repo_list.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.repo.trending.db.mediator.RepoMediator
import com.repo.trending.model.Repo
import com.repo.trending.repo.TrendingRepo
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalPagingApi::class)
class MainViewModel(private val repoMediator: RepoMediator, private val trendingRepo: TrendingRepo) : ViewModel() {

//    val data = trendingRepo.getRepos().cachedIn(viewModelScope)

    fun getRepos():Flow<PagingData<Repo>> = Pager(
        config = PagingConfig(30, enablePlaceholders = false, initialLoadSize = 60),
        remoteMediator = repoMediator
    ) {
        trendingRepo.getRepos()
    }.flow.cachedIn(viewModelScope)

}