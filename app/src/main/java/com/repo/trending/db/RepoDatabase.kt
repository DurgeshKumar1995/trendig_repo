package com.repo.trending.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.repo.trending.db.dao.RemoteMediatorRepoDao
import com.repo.trending.db.dao.RepoDao
import com.repo.trending.model.MediatorKey
import com.repo.trending.model.Repo

@Database(entities = [Repo::class,MediatorKey::class], version = 1, exportSchema = false)
@TypeConverters(OwnConverter::class)
abstract class RepoDatabase : RoomDatabase() {

    abstract fun repoDao() : RepoDao
    abstract fun remoteMediatorDao() : RemoteMediatorRepoDao
}