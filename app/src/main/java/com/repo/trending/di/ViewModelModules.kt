package com.repo.trending.di



import com.repo.trending.db.RepoDao
import com.repo.trending.impl.TrendingImpl
import com.repo.trending.repo.TrendingPagerRepo
import com.repo.trending.repo.TrendingRepo
import com.repo.trending.ui.repo_list.view_model.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module


object ViewModelModules {

    val viewModels: Module = module {

        single { provideNotePagerRepo(get()) }
        single<TrendingRepo> { TrendingImpl(get()) }
        viewModel { MainViewModel(get()) }


    }

    private fun provideNotePagerRepo(daoAPI: RepoDao): TrendingPagerRepo = TrendingPagerRepo(daoAPI)



}

