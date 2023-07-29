package com.dutch2019.util.marker

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import com.dutch2019.R
import com.dutch2019.model.LocationData
import com.dutch2019.util.*
import com.skt.Tmap.*

enum class Marker {
    CUSTOM, DEFAULT, CHANGED, NEAR, NEAR_PRIMARY, MIDDLE, CHECK, SELECT_RATIO, SELECT_RATIO_BIG, RATIO;

    fun getMark(context: Context) = when (this) {
        CUSTOM -> {
            ContextCompat.getDrawable(context, R.drawable.ic_marker_black)!!
        }
        DEFAULT -> {
            ContextCompat.getDrawable(context, R.drawable.ic_marker_gray)!!

        }
        CHANGED -> {
            ContextCompat.getDrawable(context, R.drawable.ic_marker_gray5)!!

        }
        NEAR -> {
            ContextCompat.getDrawable(context, R.drawable.marker_near)!!

        }
        NEAR_PRIMARY -> {
            ContextCompat.getDrawable(context, R.drawable.ic_marker_primary)!!
        }
        MIDDLE -> {
            ContextCompat.getDrawable(context, R.drawable.ic_marker_middle)!!

        }
        CHECK -> {
            ContextCompat.getDrawable(context, R.drawable.ic_marker_check)!!

        }
        SELECT_RATIO -> {
            ContextCompat.getDrawable(context, R.drawable.ic_marker_select_ratio)!!

        }
        SELECT_RATIO_BIG -> {
            ContextCompat.getDrawable(context, R.drawable.ic_marker_select_ratio_24)!!

        }
        RATIO -> {
            ContextCompat.getDrawable(context, R.drawable.ic_marker_ratio)!!
        }
    }
}

fun mark(tMapView: TMapView, context: Context, locationData: LocationData, number: Int) {
    tMapView.setOnMarkerClickEvent { _, _ -> }
    val markerItemPoint = TMapPoint(locationData.lat, locationData.lon)
    val markerBitmap = Marker.CUSTOM.getMark(context).toBitmap()
    val marker = MarkerOverlay(tMapView, context, locationData.name)
    drawTextOnMarker(context, markerBitmap, number.toString())
    setMarkerVariables(marker, id = locationData.name, icon = markerBitmap, point = markerItemPoint)
    tMapView.addMarkerItem2(marker.id, marker)
}

fun markNearFacility(tMapView: TMapView, context: Context, locationData: LocationData) {
    val markerImage = Marker.NEAR.getMark(context).toBitmap()
    val markerItemPoint = TMapPoint(locationData.lat, locationData.lon)
    val marker = MarkerOverlay(tMapView, context, locationData.name)
    setMarkerVariables(marker, id = locationData.name, icon = markerImage, point = markerItemPoint)
    tMapView.addMarkerItem2(marker.id, marker)
}


fun markLocationList(tMapView: TMapView, context: Context, locationList: List<LocationData>) {
    locationList.forEach { locationData ->
        mark(tMapView, context, locationData, locationList.indexOf(locationData) + 1)
    }
}

fun markMiddleLocation(tMapView: TMapView, context: Context, centerPoint: TMapPoint) {
    val markerImage = Marker.MIDDLE.getMark(context).toBitmap()
    val marker = MarkerOverlay(tMapView, context, MarkerId.MIDDLE.value)
    setMarkerVariables(marker, id = MarkerId.MIDDLE.value, icon = markerImage, point = centerPoint)
    marker.changeTextPrimaryColor(context)
    tMapView.addMarkerItem2(marker.id, marker)
}

fun markLocationCheck(tMapView: TMapView, context: Context, point: TMapPoint) {
    val markerImage = Marker.CHECK.getMark(context).toBitmap()
    val marker = TMapMarkerItem().apply {
        icon = markerImage
        tMapPoint = point
        setPosition(0.5F, 0.8F)
    }
    tMapView.addMarkerItem(marker.id, marker)
}

fun markNearFacilityList(tMapView: TMapView, context: Context, locationList: List<LocationData>) {
    locationList.forEach { locationData ->
        markNearFacility(tMapView, context, locationData)
    }
}

fun markRatioLocation(tMapView: TMapView, context: Context, centerPoint: TMapPoint) {
    tMapView.setOnMarkerClickEvent { _, _ -> }
    val markerImage = Marker.RATIO.getMark(context).toBitmap()
    val marker = MarkerOverlay(tMapView, context, MarkerId.RATIO.value)
    setMarkerVariables(marker, id = MarkerId.RATIO.value, icon = markerImage, point = centerPoint)
    marker.changeTextPrimaryColor(context)
    tMapView.addMarkerItem2(marker.id, marker)
}

fun markSelectRatioLocation(
    tMapView: TMapView, context: Context, LocationA: LocationData, LocationB: LocationData
) {
    tMapView.setOnMarkerClickEvent { _, _ -> }
    val markerImageA = Marker.SELECT_RATIO_BIG.getMark(context).toBitmap()
    val markerA = MarkerOverlay(tMapView, context, LocationA.name)
    setMarkerVariables(
        markerA, id = LocationA.name, icon = markerImageA, point = LocationA.convertTMapPoint()
    )
    drawTextOnMarker(context, markerImageA, "A")
    tMapView.addMarkerItem2(markerA.id, markerA)

    val markerImageB = Marker.SELECT_RATIO_BIG.getMark(context).toBitmap()
    val markerB = MarkerOverlay(tMapView, context, LocationB.name)
    setMarkerVariables(
        markerB, id = LocationB.name, icon = markerImageB, point = LocationB.convertTMapPoint()
    )
    drawTextOnMarker(context, markerImageB, "B")
    tMapView.addMarkerItem2(markerB.id, markerB)
}

fun removeAllNearFacilityMark(tMapView: TMapView, locationList: List<LocationData>) {
    locationList.forEach { locationData ->
        tMapView.removeMarkerItem2(locationData.name)
    }
}

fun changeDefaultNearMarks(
    tMapView: TMapView,
    context: Context,
    locationList: List<LocationData>,
    clickedMarkerItem: TMapMarkerItem2
) {
    locationList.forEach { locationData ->
        if (locationData.isNotSamePoint(clickedMarkerItem.latitude, clickedMarkerItem.longitude)) {
            tMapView.getMarkerItem2FromID(locationData.name).icon =
                Marker.NEAR.getMark(context).toBitmap()
        }
    }
}

fun findSameLocationDataFromMarkerItem(
    clickedMarkerItem: TMapMarkerItem2, locationList: List<LocationData>
): LocationData {
    locationList.forEach { locationData ->
        if (locationData.isSamePoint(clickedMarkerItem.latitude, clickedMarkerItem.longitude)) {
            return locationData
        }
    }
    return LocationData()
}

fun mapAutoZoom(tMapView: TMapView, locationList: List<LocationData>, centerPoint: TMapPoint) {
    val leftTopPoint = TMapPoint(centerPoint.latitude, centerPoint.longitude)
    val rightBottomPoint = TMapPoint(centerPoint.latitude, centerPoint.longitude)

    try {
        for (i in locationList.indices) {
            if (locationList.isNotEmpty()) {
                leftTopPoint.latitude = checkTopLat(locationList[i], leftTopPoint.latitude)
                leftTopPoint.longitude = checkTopLon(locationList[i], leftTopPoint.longitude)
                rightBottomPoint.latitude =
                    checkBottomLat(locationList[i], rightBottomPoint.latitude)
                rightBottomPoint.longitude =
                    checkBottomLon(locationList[i], rightBottomPoint.longitude)
            }
        }
        tMapView.run {
            setCenterPoint(centerPoint.longitude, centerPoint.latitude)
            // 업데이트된 API가 zoomLevel을 정상적으로 불러오지 않아서 확장함수를 사용한다.
            zoomToTMapPointPreviousVersion(leftTopPoint, rightBottomPoint)
        }
    } catch (e: Exception) {
        tMapView.setCenterPoint(centerPoint.longitude, centerPoint.latitude)
    }

}

fun checkTopLat(locationData: LocationData, lat: Double): Double {
    return if (locationData.lat >= lat) {
        locationData.lat
    } else {
        lat
    }
}

fun checkBottomLat(locationData: LocationData, lat: Double): Double {
    return if (locationData.lat <= lat) {
        locationData.lat
    } else {
        lat
    }
}

fun checkTopLon(locationData: LocationData, lon: Double): Double {
    return if (locationData.lon >= lon) {
        locationData.lon
    } else {
        lon
    }
}

fun checkBottomLon(locationData: LocationData, lon: Double): Double {
    return if (locationData.lon <= lon) {
        locationData.lon
    } else {
        lon
    }
}

fun removeAllBallon(tMapView: TMapView) {
    tMapView.apply {
        allMarkerItem2.forEach { tMapMarkerItem2 ->
            (tMapMarkerItem2 as MarkerOverlay).markerTouch = false
        }
        postInvalidate()
    }
}

fun isNotMiddleMarker(id: String) = id != MarkerId.MIDDLE.value
fun isNotRatioMarker(id: String) = id != MarkerId.RATIO.value
fun isNotLocationMarker(id: String, list: List<LocationData>): Boolean {
    list.forEach { locationData ->
        if (locationData.name == id) {
            return false
        }
    }
    return true
}

fun isNearFacilityMarker(id: String, list: List<LocationData>): Boolean =
    isNotLocationMarker(id, list) && isNotMiddleMarker(id) && isNotRatioMarker(id)

fun drawTextOnMarker(context: Context, bitmap: Bitmap, text: String) {

    val paint = Paint().apply {
        textSize = changeToDP(13, context)
        typeface = getBoldTextFont(context)
        color = Color.WHITE
        textAlign = Paint.Align.CENTER
        isAntiAlias = true
    }

    Canvas().apply {
        setBitmap(bitmap)
        drawText(
            text,
            (bitmap.width / 2).toFloat(),
            ((bitmap.height / 2) + changeToDP(2, context)),
            paint
        )
    }
}

fun drawLine(tMapView: TMapView, pointA: TMapPoint, pointB: TMapPoint) {
    TMapPolyLine().apply {
        addLinePoint(pointA)
        addLinePoint(pointB)
        tMapView.addTMapPolyLine("ratioline", this)
    }
}

fun setMarkerVariables(marker: TMapMarkerItem2, id: String, icon: Bitmap, point: TMapPoint) {
    marker.apply {
        this.id = id
        this.icon = icon
        setPosition(0.5F, 0.8F)
        tMapPoint = point
    }
}
