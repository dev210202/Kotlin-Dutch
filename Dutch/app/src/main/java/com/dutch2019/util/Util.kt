package com.dutch2019.util

import android.content.Context
import android.util.Log
import com.dutch2019.model.LocationDBData
import com.dutch2019.model.LocationDBDataList
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

fun List<LocationDBData>.convertLocationDBDataToDataList() = LocationDBDataList(value = this)

fun changeToDP(value :Int , context: Context) = value * context!!.resources.displayMetrics.density