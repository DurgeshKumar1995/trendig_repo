package com.repo.trending.di

import android.content.Context
import android.content.SharedPreferences
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

object SharedPrefrence {
    private const val shredPreferencesName = "repo_share"
    const val shredPreferencesDateKey = "repo_date"

    private fun getSharedPref(context: Context): SharedPreferences {
        return context.getSharedPreferences(shredPreferencesName, Context.MODE_PRIVATE)
    }

    val sharedPreferencesModule = module {
        single {
            getSharedPref(androidContext())
        }
    }

}