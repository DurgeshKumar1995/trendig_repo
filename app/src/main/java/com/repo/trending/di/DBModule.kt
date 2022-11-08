package com.repo.trending.di

import androidx.room.Room
import com.repo.trending.db.RepoDatabase
import com.repo.trending.db.mediator.RepoMediator
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

object DBModule {

    private const val DATABASE_NAME  = "repos_db"
    val reposAppModule = module {

        // Room Database
        single { Room.databaseBuilder(androidApplication(), RepoDatabase::class.java, DATABASE_NAME).build() }

        // BirdsDAO
        single { get<RepoDatabase>().repoDao() }
        single { get<RepoDatabase>().remoteMediatorDao() }
        factory { RepoMediator(get(),get(),get(),get()) }
    }
}