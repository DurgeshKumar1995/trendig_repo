package com.repo.trending.ui.common_model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "remote_key")
@Parcelize
data class MediatorKey(
    @PrimaryKey
    val id: Long,
    val nextKey: Int?,
    val prevKey: Int?
):Parcelable