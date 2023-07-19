package com.dutch2019.model

import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LocationDataList(
    val value : List<LocationData> = listOf()
) : Parcelable {

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    fun convertLocationData(list: List<LocationData>): LocationDataList {
        val locationDataList = mutableListOf<LocationData>()
        list.forEach { locationData ->
            if(locationData.name.isNotEmpty()) {
                locationDataList.add(locationData)
            }
        }
        return LocationDataList(value = locationDataList)
    }
}