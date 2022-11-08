package com.repo.trending.di

import androidx.room.Room
import com.repo.trending.db.RepoDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

object DBModule {

    private const val DATABASE_NAME  = "repos_db"
    val notesAppModule = module {

        // Room Database
        single { Room.databaseBuilder(androidApplication(), RepoDatabase::class.java, DATABASE_NAME).build() }

        // BirdsDAO
        single { get<RepoDatabase>().repoDao() }
        single { get<RepoDatabase>().remoteMediatorDao() }
    }
}