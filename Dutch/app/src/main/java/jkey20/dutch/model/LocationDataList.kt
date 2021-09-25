package jkey20.dutch.model

import android.os.Parcel
import android.os.Parcelable

class LocationDataList() : ArrayList<LocationData>(), Parcelable {
    constructor(parcel: Parcel) : this() {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<LocationDataList> {
        override fun createFromParcel(parcel: Parcel): LocationDataList {
            return LocationDataList(parcel)
        }

        override fun newArray(size: Int): Array<LocationDataList?> {
            return arrayOfNulls(size)
        }
    }
}