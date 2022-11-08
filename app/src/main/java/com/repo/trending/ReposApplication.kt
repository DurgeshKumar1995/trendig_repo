package com.repo.trending

import android.app.Application
import com.repo.trending.di.DBModule
import com.repo.trending.di.NetworkModule
import com.repo.trending.di.SharedPrefrence
import com.repo.trending.di.ViewModelModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin

class ReposApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@ReposApplication)
            modules(DBModule.reposAppModule, ViewModelModules.viewModels,NetworkModule.networkModule,SharedPrefrence.sharedPreferencesModule)
        }
    }

    override fun onTerminate() {
        super.onTerminate()
        stopKoin()
    }

}