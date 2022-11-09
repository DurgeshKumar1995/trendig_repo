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

    private val _updatePoint = MutableLiveData<Boolean>()
    val refreshPoint : LiveData<Boolean> = _updatePoint

    private val _recordCount = MutableLiveData<Int>()
    val recordCount : LiveData<Int> = _recordCount

    fun getFilterData(searchKey:String) {
        viewModelScope.launch {
            try {
                val data = trendingRepo.getRepoByFilterString(searchKey)
                _dataList.postValue(data)
            }catch (e:Exception){
                _dataList.postValue(null)
            }
        }
    }

    fun updateRepo(repo: Repo){
        viewModelScope.launch {
            val data = trendingRepo.update(repo)
            _updatePoint.postValue(data>0)
        }
    }

    init {
        getRepoCount()
    }

    private fun getRepoCount(){
        viewModelScope.launch {
            try {
                val count = trendingRepo.getCount()
                _recordCount.postValue(count)
            }catch (e:Exception){
                _recordCount.postValue(0)
            }

        }
    }


}