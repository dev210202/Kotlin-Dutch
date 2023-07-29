package com.dutch2019.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dutch2019.util.filtNull
import com.dutch2019.util.filtZero
import com.skt.Tmap.TMapMarkerItem2
import com.skt.Tmap.TMapPoint
import com.skt.Tmap.poi_item.TMapPOIItem
import kotlinx.android.parcel.Parcelize


@Parcelize
@Entity
data class LocationData(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "poi-id") val poiID: String = "",
    @ColumnInfo(name = "name") val name: String = "",
    @ColumnInfo(name = "address") val address: String = "",
    @ColumnInfo(name = "tel") val tel: String = "",
    @ColumnInfo(name = "lat") val lat: Double = 0.0,
    @ColumnInfo(name = "lon") val lon: Double = 0.0
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

    fun isEqualToTMapMarkerItem2(tMapMarkerItem2: TMapMarkerItem2) =
        lat == tMapMarkerItem2.latitude && lon == tMapMarkerItem2.longitude && name == tMapMarkerItem2.id

    fun setNearFacilityItem(item: TMapPOIItem): LocationData =
        LocationData(
            id = 0,
            poiID = item.poiid,
            name = item.poiName,
            address = filtNull(item.poiAddress) + filtNull(" " + item.buildingNo1) + " " + filtNull(
                filtZero(" " + item.buildingNo2)
            ),
            tel = filtNull(" " + item.telNo),
            lat = item.poiPoint.latitude,
            lon = item.poiPoint.longitude
        )

    fun isSameData(compareData: LocationData) =
        this.lat == compareData.lat && this.lon == compareData.lon

    fun isSamePoint(compareLat: Double, compareLon: Double) =
        this.lat == compareLat && this.lon == compareLon
    fun isNotSamePoint(compareLat: Double, compareLon: Double) =
        this.lat != compareLat || this.lon != compareLon
}