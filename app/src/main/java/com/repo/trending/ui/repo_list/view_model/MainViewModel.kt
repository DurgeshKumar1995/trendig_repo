package com.repo.trending.ui.repo_list.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.repo.trending.db.dao.RepoDao
import com.repo.trending.db.mediator.RepoMediator
import com.repo.trending.model.Repo
import com.repo.trending.repo.TrendingRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagingApi::class)
class MainViewModel(private val repoMediator: RepoMediator, private val trendingRepo: RepoDao) : ViewModel() {


    private val _updatePoint = MutableLiveData<Boolean>()
    val refreshPoint : LiveData<Boolean> = _updatePoint

    fun getRepos():Flow<PagingData<Repo>> = Pager(
        config = PagingConfig(30, enablePlaceholders = false),
        remoteMediator = repoMediator
    ) {
        trendingRepo.getRepos()
    }.flow.cachedIn(viewModelScope)


    fun updateRepo(repo: Repo){
        viewModelScope.launch {
           val value = trendingRepo.update(repo)
            _updatePoint.postValue(value>0)
        }
    }

}