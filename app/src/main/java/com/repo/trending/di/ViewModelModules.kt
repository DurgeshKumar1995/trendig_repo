package com.repo.trending.di



import com.repo.trending.impl.MediatorKeyImpl
import com.repo.trending.impl.TrendingDBImpl
import com.repo.trending.impl.TrendRESTAPIImpl
import com.repo.trending.repo.MediatorKeyRepo
import com.repo.trending.repo.TrendRESTAPIRepo
import com.repo.trending.repo.TrendingDBRepo
import com.repo.trending.ui.filter.view_model.FilterViewModel
import com.repo.trending.ui.repo_list.view_model.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module


object ViewModelModules {

    val viewModels: Module = module {

        single<TrendRESTAPIRepo> { TrendRESTAPIImpl(get()) }
        single<TrendingDBRepo> { TrendingDBImpl(get()) }
        single<MediatorKeyRepo> { MediatorKeyImpl(get()) }
        viewModel { MainViewModel(get(),get()) }
        viewModel { FilterViewModel(get()) }

    }


}

