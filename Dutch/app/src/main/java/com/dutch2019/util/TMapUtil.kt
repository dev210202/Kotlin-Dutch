package com.dutch2019.util

import com.dutch2019.model.LocationData
import com.skt.Tmap.MapUtils
import com.skt.Tmap.TMapPoint
import com.skt.Tmap.TMapView
import com.skt.Tmap.poi_item.TMapPOIItem

fun convertTMapPOIItemToLocationData(tMapPOIItem: TMapPOIItem): LocationData = LocationData(
    poiID = tMapPOIItem.poiid,
    name = tMapPOIItem.poiName,
    address = tMapPOIItem.poiAddress,
    tel = tMapPOIItem.telNo,
    lat = tMapPOIItem.noorLat.toDouble(),
    lon = tMapPOIItem.noorLon.toDouble(),
)

fun isItemDataOK(item: TMapPOIItem) =
    item.poiName != null && item.upperAddrName != null && item.poiPoint != null
fun calculateCenterPoint(locationList: List<LocationData>): TMapPoint {
    var totalLat = 0.0
    var totalLon = 0.0
    locationList.forEach { data ->
        totalLat += data.lat
        totalLon += data.lon
    }
    return TMapPoint(totalLat / locationList.size, totalLon / locationList.size)
}

fun calculateCenterPoint(locationA : TMapPoint, locationB : TMapPoint): TMapPoint {
    return TMapPoint((locationA.latitude + locationB.latitude) / 2, (locationA.longitude + locationB.longitude) / 2)
}
fun getCalculatedRatioPoint(point1: TMapPoint, point2: TMapPoint, ratioValue: Int): TMapPoint {
    var changeLat = 0.0
    var changeLon = 0.0
    when {
        ratioValue == 5 -> {
            changeLat = (point1.latitude + point2.latitude) / 2
            changeLon = (point1.longitude + point2.longitude) / 2
        }
        is1stQuadrant(point1, point2) -> {
            changeLat = ((10 - ratioValue) * point1.latitude + (ratioValue) * point2.latitude) / 10
            changeLon = ((10 - ratioValue) * point1.longitude + ratioValue * point2.longitude) / 10
        }
        is2ndQuadrant(point1, point2) -> {
            changeLat = ((10 - ratioValue) * point1.latitude + ratioValue * point2.latitude) / 10
            changeLon = (ratioValue * point2.longitude + (10 - ratioValue) * point1.longitude) / 10
        }
        is3rdQuadrant(point1, point2) -> {
            changeLat = (ratioValue * point2.latitude + (10 - ratioValue) * point1.latitude) / 10
            changeLon = (ratioValue * point2.longitude + (10 - ratioValue) * point1.longitude) / 10
        }
        is4thQuadrant(point1, point2) -> {
            changeLat = (ratioValue * point2.latitude + (10 - ratioValue) * point1.latitude) / 10
            changeLon = ((10 - ratioValue) * point1.longitude + ratioValue * point2.longitude) / 10
        }
    }
    return TMapPoint(changeLat, changeLon)
}


private fun is1stQuadrant(point1: TMapPoint, point2: TMapPoint) =
    (point1.latitude < point2.latitude && point1.longitude < point2.longitude)

private fun is2ndQuadrant(point1: TMapPoint, point2: TMapPoint) =
    (point1.latitude > point2.latitude && point1.longitude < point2.longitude)

private fun is3rdQuadrant(point1: TMapPoint, point2: TMapPoint) =
    (point1.latitude > point2.latitude && point1.longitude > point2.longitude)

private fun is4thQuadrant(point1: TMapPoint, point2: TMapPoint) =
    (point1.latitude < point2.latitude && point1.longitude > point2.longitude)

fun TMapView.zoomToTMapPointPreviousVersion(leftTop: TMapPoint, rightBottom: TMapPoint) {
    val minPointX = TMapPoint(leftTop.latitude, leftTop.longitude)
    val maxPointX = TMapPoint(rightBottom.latitude, leftTop.longitude)
    val minPointY = TMapPoint(leftTop.latitude, rightBottom.longitude)
    val maxPointY = TMapPoint(leftTop.latitude, leftTop.longitude)
    val findZoom: Int = this.findPreviousVersionZoomLevel(minPointX, maxPointX, minPointY, maxPointY)
    this.zoomLevel = findZoom
}
fun TMapView.findPreviousVersionZoomLevel(
    minPointX: TMapPoint,
    maxPointX: TMapPoint,
    minPointY: TMapPoint,
    maxPointY: TMapPoint
): Int {
    val DisX = MapUtils.getDistance(minPointX, maxPointX)
    val DisY = MapUtils.getDistance(minPointY, maxPointY)
    val mScaleInfo = doubleArrayOf(
        156543.033928041,
        78271.51696402048,
        39135.75848201023,
        19567.87924100512,
        9783.93962050256,
        4891.96981025128,
        2445.98490512564,
        1222.99245256282,
        611.49622628141,
        305.7481131407048,
        152.8740565703525,
        76.43702828517624,
        38.21851414258813,
        19.10925707129406,
        9.554628535647032,
        4.777314267823516,
        2.388657133911758,
        1.194328566955879,
        0.5971642834779395,
        0.2985821417389698
    )
    for (i in this.maximumShownMapZoom downTo this.minimumShownMapZoom) {
        val mapDis = mScaleInfo[i] * 256.0
        if (DisX < mapDis && DisY < mapDis) {
            return i
        }
    }
    return 0
}