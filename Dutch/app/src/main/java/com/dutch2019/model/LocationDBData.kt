package com.dutch2019.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class LocationDBData(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    @ColumnInfo(name = "centerPointLat")
    val centerPointLat: Double,
    @ColumnInfo(name = "centerPointLon")
    val centerPointLon: Double,
    @ColumnInfo(name = "centerAddress")
    val centerAddress: String,
    @ColumnInfo(name = "locations")
    val locations: List<LocationData> = listOf<LocationData>()
) : Parcelable {
    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(parcel: Parcel, p1: Int) {
        parcel.writeInt(id)
        parcel.writeDouble(centerPointLat)
        parcel.writeDouble(centerPointLon)
        parcel.writeString(centerAddress)
        parcel.writeList(locations)

    }
}