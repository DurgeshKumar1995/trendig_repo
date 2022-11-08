package com.repo.trending.ui.repo_list.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.repo.trending.repo.TrendingRepo

class MainViewModel( noteRepo: TrendingRepo) : ViewModel() {

    val data = noteRepo.getNotes().cachedIn(viewModelScope)


    private val _refreshPoint = MutableLiveData<Boolean>()
    val refreshPoint :LiveData<Boolean> = _refreshPoint
}