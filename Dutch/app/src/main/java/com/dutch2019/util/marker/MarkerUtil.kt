package com.dutch2019.util.marker

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toBitmap
import com.dutch2019.R
import com.dutch2019.model.LocationData
import com.dutch2019.util.MarkerId
import com.dutch2019.util.changeToDP
import com.skt.Tmap.TMapMarkerItem2
import com.skt.Tmap.TMapPoint
import com.skt.Tmap.TMapPolyLine
import com.skt.Tmap.TMapView


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
    marker.changeTextRedColor(context)
    tMapView.addMarkerItem2(marker.id, marker)
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
    marker.changeTextRedColor(context)
    tMapView.addMarkerItem2(marker.id, marker)
}
fun markSelectRatioLocation(tMapView: TMapView, context: Context, LocationA: LocationData, LocationB: LocationData){
    tMapView.setOnMarkerClickEvent { p0, p1 -> }
    val markerImageA = getSelectRatioBigMarkerBackground(context).toBitmap()
    val markerA = MarkerOverlay(tMapView, context, LocationA.name)
    setMarkerVariables(markerA, id = LocationA.name, icon = markerImageA, point = LocationA.convertTMapPoint())
    drawTextOnMarker(context, markerImageA, "A")
    tMapView.addMarkerItem2(markerA.id, markerA)

    val markerImageB = getSelectRatioBigMarkerBackground(context).toBitmap()
    val markerB = MarkerOverlay(tMapView, context, LocationB.name)
    setMarkerVariables(markerB, id = LocationB.name, icon = markerImageB, point = LocationB.convertTMapPoint())
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
    var leftTopLat = centerPoint.latitude
    var leftTopLon = centerPoint.longitude
    var rightBottomLat = centerPoint.latitude
    var rightBottomLon = centerPoint.longitude
    try {
        for (i in locationList.indices) {
            if (locationList.isNotEmpty()) {
                leftTopLat = checkTopLat(locationList[i], leftTopLat)
                leftTopLon = checkBottomLon(locationList[i], leftTopLon)
                rightBottomLat = checkBottomLat(locationList[i], rightBottomLat)
                rightBottomLon = checkTopLon(locationList[i], rightBottomLon)
            }

        }
        val leftTopPoint = TMapPoint(leftTopLat, leftTopLon)
        val rightBottomPoint = TMapPoint(rightBottomLat, rightBottomLon)

        tMapView.setCenterPoint(centerPoint.longitude, centerPoint.latitude)
        tMapView.zoomToTMapPoint(leftTopPoint, rightBottomPoint)
        tMapView.zoomLevel = tMapView.zoomLevel - 2
    } catch (e: Exception) {
        e.printStackTrace()
        tMapView.setCenterPoint(centerPoint.longitude, centerPoint.latitude)
    }

}

fun setMarkRatioLocation(tMapView: TMapView, context: Context, ratioPoint: TMapPoint) {

    val markerImage = BitmapFactory.decodeResource(context.resources, R.drawable.ic_marker_blue)


    val marker = MarkerOverlay(tMapView, context, "비율변경지점")

    val strId = "비율변경지점"
    marker.id = strId
    marker.chagneTextBlueColor(context)
    marker.icon = markerImage
    marker.setPosition(0.5F, 0.8F)
    marker.tMapPoint = ratioPoint

    tMapView.addMarkerItem2(strId, marker)
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
    tMapView.allMarkerItem2.forEach { tMapMarkerItem2 ->
        val item = tMapMarkerItem2 as MarkerOverlay
        item.markerTouch = false
    }
    tMapView.postInvalidate()
}

fun changeNearPrimaryMark(tMapMarkerItem: TMapMarkerItem2, context: Context) {
    tMapMarkerItem.icon =
        ContextCompat.getDrawable(context, R.drawable.ic_marker_primary)!!.toBitmap()
}

fun changeSelectRatioMark(tMapMarkerItem: TMapMarkerItem2, context: Context) {
    tMapMarkerItem.icon = getSelectRatioBigMarkerBackground(context).toBitmap()

}

fun isNotMiddleMarker(id: String) = id != MarkerId.MIDDLE

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

fun getChangedtMarkerBackground(context: Context) =
    ContextCompat.getDrawable(context, R.drawable.ic_marker_gray5)!!

fun getNearMarkerBackground(context: Context) =
    ContextCompat.getDrawable(context, R.drawable.marker_near)!!

fun getMiddleMarkerBackground(context: Context) =
    ContextCompat.getDrawable(context, R.drawable.ic_marker_middle)!!

fun getSelectRatioBigMarkerBackground(context: Context) =
    ContextCompat.getDrawable(context, R.drawable.ic_marker_select_ratio_24)!!

fun getSelectRatioMarkerBackground(context: Context) =
    ContextCompat.getDrawable(context, R.drawable.ic_marker_select_ratio)!!

fun getRatioMarkerBackground(context: Context) =
    ContextCompat.getDrawable(context, R.drawable.ic_marker_ratio)!!

fun drawTextOnMarker(context: Context, bitmap: Bitmap, text: String) {
    val canvas = Canvas()

    val paint = Paint()
    val customTypeface = ResourcesCompat.getFont(context, R.font.pretendard_bold)

    paint.apply {
        textSize = changeToDP(13, context)
        typeface = customTypeface
        color = Color.WHITE
        textAlign = Paint.Align.CENTER
        isAntiAlias = true
    }

    canvas.setBitmap(bitmap)
    canvas.drawText(
        text,
        (bitmap.width / 2).toFloat(),
        ((bitmap.height / 2) + changeToDP(2, context)),
        paint
    )
}

fun drawLine(tMapView: TMapView, pointA: TMapPoint, pointB: TMapPoint){
    val polyLine = TMapPolyLine()
    //polyLine.lineColor =
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
