package com.dutch2019.util

import com.dutch2019.model.LocationData
import com.skt.Tmap.poi_item.TMapPOIItem

fun convertTMapPOIItemToLocationData(tMapPOIItem: TMapPOIItem): LocationData = LocationData(
    poiID = tMapPOIItem.poiid,
    name = tMapPOIItem.poiName,
    address = tMapPOIItem.poiAddress,
    tel = tMapPOIItem.telNo,
    lat = tMapPOIItem.noorLat.toDouble(),
    lon = tMapPOIItem.noorLon.toDouble(),
)
