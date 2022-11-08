package com.repo.trending.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.util.*

@Entity(tableName = "key")
@Parcelize
data class MediaterKey(
    @PrimaryKey
    val id: String,
    val nextKey: Int,
    val prevKey: Int
):Parcelable