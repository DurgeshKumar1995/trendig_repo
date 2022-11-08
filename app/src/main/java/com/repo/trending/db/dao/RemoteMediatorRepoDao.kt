package com.repo.trending.db.dao

import androidx.room.*
import com.repo.trending.model.MediatorKey

@Dao
interface RemoteMediatorRepoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(dogs:List<MediatorKey>)

    @Query("SELECT * FROM remote_key WHERE id =:id ")
    suspend fun getMediatorKey(id:Long):MediatorKey

    @Query("DELETE FROM remote_key")
    suspend fun clearAll()
}