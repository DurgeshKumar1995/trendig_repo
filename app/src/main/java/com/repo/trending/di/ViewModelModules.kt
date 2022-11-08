package com.repo.trending.di



import com.repo.trending.impl.MediatorKeyImpl
import com.repo.trending.impl.TrendingImpl
import com.repo.trending.impl.TrendAPIImpl
import com.repo.trending.repo.MediatorKeyRepo
import com.repo.trending.repo.TrendAPIRepo
import com.repo.trending.repo.TrendingRepo
import com.repo.trending.ui.filter.view_model.FilterViewModel
import com.repo.trending.ui.repo_list.view_model.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module


object ViewModelModules {

    val viewModels: Module = module {

        single<TrendAPIRepo> { TrendAPIImpl(get()) }
        single<TrendingRepo> { TrendingImpl(get()) }
        single<MediatorKeyRepo> { MediatorKeyImpl(get()) }
        viewModel { MainViewModel(get(),get()) }
        viewModel { FilterViewModel(get()) }

    }


}

