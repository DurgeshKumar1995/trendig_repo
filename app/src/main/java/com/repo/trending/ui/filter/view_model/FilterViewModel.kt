package com.repo.trending.ui.filter.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.repo.trending.model.Repo
import com.repo.trending.repo.TrendingDBRepo
import kotlinx.coroutines.launch

class FilterViewModel( private val trendingRepo: TrendingDBRepo) : ViewModel() {


    private val _dataList = MutableLiveData<List<Repo>>()
    val dataList : LiveData<List<Repo>> = _dataList

    fun getFilterData(searchKey:String) {
        viewModelScope.launch {
          val data =  trendingRepo.getRepoByFilterString(searchKey)
            _dataList.postValue(data)
        }
    }

    fun updateRepo(repo: Repo){
        viewModelScope.launch {
            trendingRepo.update(repo)
        }
    }


}