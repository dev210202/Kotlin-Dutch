package com.dutch2019.model

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LocationSearchDataList(
    val value: List<LocationSearchData>
) : Parcelable {




    fun convertLocationSearchDataListToData(): List<LocationSearchData> {
        return value
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(parcel: Parcel, p1: Int) {
        parcel.writeList(value)
    }


}

