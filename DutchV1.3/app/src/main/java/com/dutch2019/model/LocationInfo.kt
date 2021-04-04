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
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    @ColumnInfo(name = "name")
    var name: String,
    @ColumnInfo(name = "address")
    var adress: String,
    @ColumnInfo(name = "lat")
    var latitude: Double,
    @ColumnInfo(name = "lon")
    var longitude: Double
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readDouble(),
        parcel.readDouble()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(adress)
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
