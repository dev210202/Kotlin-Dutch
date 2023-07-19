package com.dutch2019.util

import android.content.Context
import com.dutch2019.model.LocationSearchData
import com.dutch2019.model.LocationSearchDataList
import com.dutch2019.model.LocationData

fun Any?.isNotNull() = this != null

fun List<LocationData>.getLocationsName(): String {
    var locationsName = ""
    this.forEach { locationData ->
        locationsName += if (locationsName.isNotEmpty()) {
            " - " + locationData.name
        } else {
            locationData.name
        }
    }
    return locationsName
}

fun List<LocationSearchData>.convertLocationDBDataToDataList() = LocationSearchDataList(value = this)

fun changeToDP(value :Int , context: Context) = value * context.resources.displayMetrics.density