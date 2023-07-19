package com.dutch2019.util.marker

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import com.dutch2019.R
import com.dutch2019.model.LocationData
import com.dutch2019.util.MarkerId
import com.dutch2019.util.changeToDP
import com.dutch2019.util.getBoldTextFont
import com.dutch2019.util.zoomToTMapPointPreviousVersion
import com.skt.Tmap.*


fun mark(tMapView: TMapView, context: Context, locationData: LocationData, number: Int) {
    tMapView.setOnMarkerClickEvent { p0, p1 -> }
    val markerItemPoint = TMapPoint(locationData.lat, locationData.lon)
    val markerBitmap = getCustomMarkerBackground(context).toBitmap()
    val marker = MarkerOverlay(tMapView, context, locationData.name)
    drawTextOnMarker(context, markerBitmap, number.toString())
    setMarkerVariables(marker, id = locationData.name, icon = markerBitmap, point = markerItemPoint)
    tMapView.addMarkerItem2(marker.id, marker)
}

fun markNearFacility(tMapView: TMapView, context: Context, locationData: LocationData) {
    val markerImage = getNearMarkerBackground(context).toBitmap()
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
    val markerImage = getMiddleMarkerBackground(context).toBitmap()

    val marker = MarkerOverlay(tMapView, context, MarkerId.MIDDLE)
    setMarkerVariables(marker, id = MarkerId.MIDDLE, icon = markerImage, point = centerPoint)
    marker.changeTextPrimaryColor(context)
    tMapView.addMarkerItem2(marker.id, marker)
}

fun markLocationCheck(tMapView: TMapView, context: Context, point: TMapPoint){
    val markerImage = getLocationCheckMarkerBackground(context).toBitmap()
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
    tMapView.setOnMarkerClickEvent { p0, p1 -> }
    val markerImage = getRatioMarkerBackground(context).toBitmap()
    val marker = MarkerOverlay(tMapView, context, MarkerId.RATIO)
    setMarkerVariables(marker, id = MarkerId.RATIO, icon = markerImage, point = centerPoint)
    marker.changeTextPrimaryColor(context)
    tMapView.addMarkerItem2(marker.id, marker)
}

fun markSelectRatioLocation(
    tMapView: TMapView, context: Context, LocationA: LocationData, LocationB: LocationData
) {
    tMapView.setOnMarkerClickEvent { p0, p1 -> }
    val markerImageA = getSelectRatioBigMarkerBackground(context).toBitmap()
    val markerA = MarkerOverlay(tMapView, context, LocationA.name)
    setMarkerVariables(
        markerA, id = LocationA.name, icon = markerImageA, point = LocationA.convertTMapPoint()
    )
    drawTextOnMarker(context, markerImageA, "A")
    tMapView.addMarkerItem2(markerA.id, markerA)

    val markerImageB = getSelectRatioBigMarkerBackground(context).toBitmap()
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
        if (isSameLocation(clickedMarkerItem, locationData)) {
            val markerItem = tMapView.getMarkerItem2FromID(locationData.name)
            markerItem.icon = getNearMarkerBackground(context).toBitmap()
        }
    }
}

fun mapAutoZoom(tMapView: TMapView, locationList: List<LocationData>, centerPoint: TMapPoint) {
    val leftTopPoint = TMapPoint(centerPoint.latitude, centerPoint.longitude)
    val rightBottomPoint = TMapPoint( centerPoint.latitude, centerPoint.longitude)

    try {
        for (i in locationList.indices) {
            if (locationList.isNotEmpty()) {
                leftTopPoint.latitude = checkTopLat(locationList[i], leftTopPoint.latitude)
                leftTopPoint.longitude = checkTopLon(locationList[i], leftTopPoint.longitude)
                rightBottomPoint.latitude = checkBottomLat(locationList[i], rightBottomPoint.latitude)
                rightBottomPoint.longitude = checkBottomLon(locationList[i], rightBottomPoint.longitude)
            }
        }
        tMapView.setCenterPoint(centerPoint.longitude, centerPoint.latitude)
        // 업데이트된 API가 zoomLevel을 정상적으로 불러오지 않아서 확장함수를 사용한다.
        tMapView.zoomToTMapPointPreviousVersion(leftTopPoint, rightBottomPoint)
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

fun changeNearPrimaryMark(tMapMarkerItem: TMapMarkerItem2, context: Context) {
    tMapMarkerItem.icon =
        ContextCompat.getDrawable(context, R.drawable.ic_marker_primary)!!.toBitmap()
}

fun changeSelectRatioMark(tMapMarkerItem: TMapMarkerItem2, context: Context) {
    tMapMarkerItem.icon = getSelectRatioBigMarkerBackground(context).toBitmap()

}

fun isNotMiddleMarker(id: String) = id != MarkerId.MIDDLE
fun isNotRatioMarker(id:String) = id != MarkerId.RATIO
fun isNotLocationMarker(id: String, list: List<LocationData>): Boolean {
    list.forEach { locationData ->
        if (locationData.name == id) {
            return false
        }
    }
    return true
}


fun getCustomMarkerBackground(context: Context) =
    ContextCompat.getDrawable(context, R.drawable.ic_marker_black)!!

fun getDefaultMarkerBackground(context: Context) =
    ContextCompat.getDrawable(context, R.drawable.ic_marker_gray)!!

fun getChangedMarkerBackground(context: Context) =
    ContextCompat.getDrawable(context, R.drawable.ic_marker_gray5)!!

fun getNearMarkerBackground(context: Context) =
    ContextCompat.getDrawable(context, R.drawable.marker_near)!!

fun getMiddleMarkerBackground(context: Context) =
    ContextCompat.getDrawable(context, R.drawable.ic_marker_middle)!!

fun getLocationCheckMarkerBackground(context: Context) =
    ContextCompat.getDrawable(context, R.drawable.ic_marker_check)!!
fun getSelectRatioBigMarkerBackground(context: Context) =
    ContextCompat.getDrawable(context, R.drawable.ic_marker_select_ratio_24)!!

fun getSelectRatioMarkerBackground(context: Context) =
    ContextCompat.getDrawable(context, R.drawable.ic_marker_select_ratio)!!

fun getRatioMarkerBackground(context: Context) =
    ContextCompat.getDrawable(context, R.drawable.ic_marker_ratio)!!

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
    val polyLine = TMapPolyLine()
    polyLine.addLinePoint(pointA)
    polyLine.addLinePoint(pointB)
    tMapView.addTMapPolyLine("ratioline", polyLine)
}

fun setMarkerVariables(marker: TMapMarkerItem2, id: String, icon: Bitmap, point: TMapPoint) {
    marker.id = id
    marker.icon = icon
    marker.setPosition(0.5F, 0.8F)
    marker.tMapPoint = point
}

private fun isSameLocation(tMapMarkerItem: TMapMarkerItem2, locationData: LocationData) =
    locationData.lat != tMapMarkerItem.latitude && locationData.lon != tMapMarkerItem.longitude
