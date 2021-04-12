package com.dutch2019.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dutch2019.db.LocationInfoDao
import java.io.Serializable

@Entity
data class LocationInfo(
    var id: Int = 0,

    var name: String = "위치를 설정해주세요",
    var address: String = "",
    var latitude: Double = 0.0 ,
    var longitude: Double = 0.0
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readDouble(),
        parcel.readDouble()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(address)
        parcel.writeDouble(latitude)
        parcel.writeDouble(longitude)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<LocationInfo> {
        override fun createFromParcel(parcel: Parcel): LocationInfo {
            return LocationInfo(parcel)
        }

        override fun newArray(size: Int): Array<LocationInfo?> {
            return arrayOfNulls(size)
        }
    }
}
