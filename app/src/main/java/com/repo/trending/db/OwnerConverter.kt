package com.repo.trending.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.repo.trending.ui.common_model.Owner

class OwnerConverter {
    @TypeConverter
    fun getOwner(value: String?): Owner? {
        return value?.let { Gson().fromJson(it, Owner::class.java) }
    }

    @TypeConverter
    fun ownerToString(owner: Owner?): String? {
        return owner?.run {
            Gson().toJson(this)
        }
    }
}