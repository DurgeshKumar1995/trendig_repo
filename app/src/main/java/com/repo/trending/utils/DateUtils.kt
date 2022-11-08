package com.repo.trending.utils

import java.text.SimpleDateFormat
import java.util.Date

object DateUtils {

    @JvmStatic
    fun getDate(date: Date): String {//2006-11-17
        return try {
            SimpleDateFormat("dd/MM/yyyy").format(date)
        } catch (e: Exception) {
            "N/A"
        }
    }



}