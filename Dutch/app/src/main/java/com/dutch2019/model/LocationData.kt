package com.dutch2019.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity

@Entity
data class LocationData(
    var poiID: String = "",
    var name: String = "",
    var address: String = "",
    var tel : String = "",
    var lat: Double = 0.0,
    var lon: Double = 0.0
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readDouble(),
        parcel.readDouble()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(poiID)
        parcel.writeString(name)
        parcel.writeString(address)
        parcel.writeString(tel)
        parcel.writeDouble(lat)
        parcel.writeDouble(lon)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<LocationData> {
        override fun createFromParcel(parcel: Parcel): LocationData {
            return LocationData(parcel)
        }

        override fun newArray(size: Int): Array<LocationData?> {
            return arrayOfNulls(size)
        }
    }
}