package com.repo.trending.db

import androidx.paging.PagingSource
import androidx.room.*
import com.repo.trending.model.Repo

@Dao
interface RepoDao {
    @Query("SELECT * FROM repo ORDER BY id ASC LIMIT :limit OFFSET :offset")
    suspend fun getNoteList(limit: Int, offset: Int): List<Repo>

    @Transaction
    @Query("SELECT * FROM repo ORDER BY createdDate DESC")
    fun getNotes(): PagingSource<Int,Repo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: Repo): Long

    @Delete
    suspend fun delete(item: Repo): Int

    @Update
    suspend fun update(item: Repo): Int
}