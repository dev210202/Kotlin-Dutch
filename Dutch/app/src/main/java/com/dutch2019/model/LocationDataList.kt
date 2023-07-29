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
        parcel.writeList(value)
    }
    override fun describeContents(): Int {
        return 0
    }

    fun convertLocationData(list: List<LocationData>): LocationDataList {
        mutableListOf<LocationData>().apply {
            list.forEach { locationData ->
                if(locationData.name.isNotEmpty()) {
                    this.add(locationData)
                }
            }
        }.run {
            return LocationDataList(value = this)
        }
    }
}