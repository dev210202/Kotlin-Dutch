package com.dutch2019.util.marker

import android.content.Context
import android.graphics.*
import android.graphics.Paint.Align
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toBitmap
import com.skt.Tmap.TMapPoint
import com.skt.Tmap.TMapView
import com.dutch2019.R
import com.dutch2019.model.LocationData
import com.dutch2019.util.changeToDP
import com.dutch2019.util.markerIdValue
import java.lang.Exception


fun mark(tMapView: TMapView, context: Context, locationData: LocationData, number: Int) {
    val markerItemPoint = TMapPoint(locationData.lat, locationData.lon)
    val drawable = ContextCompat.getDrawable(context, R.drawable.ic_marker_black)
    val bitmap = drawable!!.toBitmap()

    val canvas = Canvas()

    val paint = Paint()
    val text = number.toString()
    val customTypeface = ResourcesCompat.getFont(context, R.font.pretendard_bold)

    paint.apply {
        textSize = changeToDP(13, context)
        typeface = customTypeface
        color = Color.WHITE
        textAlign = Paint.Align.CENTER
        isAntiAlias = true
    }

    canvas.setBitmap(bitmap)
    canvas.drawText(text, (bitmap.width / 2).toFloat(), ((bitmap.height / 2) + changeToDP(2, context)), paint)

    val marker = MarkerOverlay(
        tMapView, context, locationData.name
    )

    marker.id = locationData.name
    marker.icon = bitmap
    marker.setPosition(0.5F, 0.8F)
    marker.tMapPoint = markerItemPoint
    tMapView.addMarkerItem2(marker.id, marker)
}

fun markLocationList(tMapView: TMapView, context: Context, locationList: List<LocationData>) {
    locationList.forEach { locationData ->
        mark(tMapView, context, locationData, locationList.indexOf(locationData) + 1)
    }
}

fun markMiddleLocation(tMapView: TMapView, context: Context, centerPoint: TMapPoint) {
    val markerImage = ContextCompat.getDrawable(context, R.drawable.ic_marker_middle)!!.toBitmap()

    val marker = MarkerOverlay(tMapView, context, markerIdValue.MIDDLE)
    val strId = markerIdValue.MIDDLE
    marker.id = strId
    marker.changeTextRedColor(context)
    marker.icon = markerImage
    marker.setPosition(0.5F, 1F)
    marker.tMapPoint = centerPoint
    tMapView.addMarkerItem2(strId, marker)
}


fun mapAutoZoom(
    tMapView: TMapView, locationList: List<LocationData>, centerPoint: TMapPoint, context: Context
) {
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

    val markerImage = BitmapFactory.decodeResource(
        context.resources, R.drawable.ic_marker_blue
    )


    val marker = MarkerOverlay(tMapView, context, "비율변경지점")

    val strId = "비율변경지점"
    marker.id = strId
    marker.chagneTextBlueColor(context)
    marker.icon = markerImage
    marker.setPosition(0.5F, 0.8F)
    marker.tMapPoint = ratioPoint

    tMapView.addMarkerItem2(strId, marker)
}

fun getDefaultMarkerBackground(context: Context) =
    ContextCompat.getDrawable(context, R.drawable.ic_marker_gray)

fun getChangedtMarkerBackground(context: Context) =
    ContextCompat.getDrawable(context, R.drawable.ic_marker_gray5)

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
