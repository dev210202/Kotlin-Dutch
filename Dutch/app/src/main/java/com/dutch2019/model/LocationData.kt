package com.dutch2019.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.skt.Tmap.TMapMarkerItem2
import com.skt.Tmap.TMapPoint
import kotlinx.android.parcel.Parcelize


@Parcelize
@Entity
data class LocationData(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @ColumnInfo(name = "poi-id") var poiID: String = "",
    @ColumnInfo(name = "name") var name: String = "",
    @ColumnInfo(name = "address") var address: String = "",
    @ColumnInfo(name = "tel") var tel: String = "",
    @ColumnInfo(name = "lat") var lat: Double = 0.0,
    @ColumnInfo(name = "lon") var lon: Double = 0.0
) : Parcelable {


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

    fun convertTMapPoint() = TMapPoint(lat, lon)

    fun isEqualToTMapMarkerItem2(tMapMarkerItem2: TMapMarkerItem2) = lat == tMapMarkerItem2.latitude && lon == tMapMarkerItem2.longitude && name == tMapMarkerItem2.id

}