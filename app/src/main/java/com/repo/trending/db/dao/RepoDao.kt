package com.repo.trending.db.dao

import androidx.paging.PagingSource
import androidx.room.*
import com.repo.trending.model.Repo

@Dao
interface RepoDao {

    @Transaction
    @Query("SELECT * FROM repo")
    fun getRepos(): PagingSource<Int,Repo>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<Repo>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: Repo): Long

    @Delete
    suspend fun delete(item: Repo): Int

    @Update
    suspend fun update(item: Repo): Int

    @Query("DELETE FROM repo")
    suspend fun clearAll()
}