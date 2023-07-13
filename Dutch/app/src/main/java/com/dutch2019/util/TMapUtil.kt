package com.dutch2019.util

import com.dutch2019.model.LocationData
import com.skt.Tmap.TMapMarkerItem2
import com.skt.Tmap.poi_item.TMapPOIItem

fun convertTMapPOIItemToLocationData(tMapPOIItem: TMapPOIItem): LocationData = LocationData(
    poiID = tMapPOIItem.poiid,
    name = tMapPOIItem.poiName,
    address = tMapPOIItem.poiAddress,
    tel = tMapPOIItem.telNo,
    lat = tMapPOIItem.noorLat.toDouble(),
    lon = tMapPOIItem.noorLon.toDouble(),
)


fun TMapMarkerItem2.copy(): TMapMarkerItem2 {
    return TMapMarkerItem2().apply {
        icon = this@copy.icon
        latitude = this@copy.latitude
        longitude = this@copy.longitude
        tMapPoint = this@copy.tMapPoint
        id = this@copy.id
    }

}
//  longitude = 0.0;
//    latitude = 0.0;
//    Icon;
//    radius = 15;
//    <Bitmap> animationList = new ArrayList();
//    nDurationTime = 1000;
//    id = "";
//    CenterX = 0.0F;
//    CenterY = 0.0F;
//    mMarkerTouch = false;
//    CalloutRect = null;