package com.repo.trending.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.repo.trending.model.MediaterKey
import com.repo.trending.model.Repo

@Database(entities = [Repo::class,MediaterKey::class], version = 1, exportSchema = false)
@TypeConverters(DateConverter::class)
abstract class RepoDatabase : RoomDatabase() {

    abstract fun repoDao() : RepoDao
    abstract fun remoteMediatorDao() : RemoteMediatorRepoDao
}