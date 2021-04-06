package com.dutch2019.model

import android.os.Parcel
import android.os.Parcelable

data class LocationPoint (
    var latitude : Double,
    var longitude : Double
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readDouble(),
        parcel.readDouble()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeDouble(latitude)
        parcel.writeDouble(longitude)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<LocationPoint> {
        override fun createFromParcel(parcel: Parcel): LocationPoint {
            return LocationPoint(parcel)
        }

        override fun newArray(size: Int): Array<LocationPoint?> {
            return arrayOfNulls(size)
        }
    }
}