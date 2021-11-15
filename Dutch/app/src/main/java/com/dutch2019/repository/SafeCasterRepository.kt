package com.dutch2019.repository

import com.dutch2019.network.SafeCasterService
import java.text.SimpleDateFormat
import java.util.*

class SafeCasterRepository(private val api: SafeCasterService) {
    var today = getTodayDate()
    suspend fun getSafetyIndex(zipCode: String): Double {
        val data = api.searchSafetyIndexByZipCode(today, zipCode)
        return data.data[getCurrentHour()].contactDensityPercentile
    }

    private fun getTodayDate(): String {
        val now = System.currentTimeMillis()
        val date = Date(now)
        val format = SimpleDateFormat("yyyyMMdd")
        return format.format(date)
    }

    private fun getCurrentHour(): Int {
        val now = System.currentTimeMillis()
        val date = Date(now)
        val format = SimpleDateFormat("hh")
        return format.format(date).toInt()
    }
}