package com.dutch2019.util.marker

import android.content.Context
import android.graphics.BitmapFactory
import androidx.core.content.ContextCompat
import com.skt.Tmap.TMapPoint
import com.skt.Tmap.TMapView
import com.dutch2019.R
import com.dutch2019.model.LocationData
import java.lang.Exception

fun markLocationList(tMapView: TMapView, context: Context, locationList : ArrayList<LocationData>){
    locationList.forEach { locationData ->
        val markerItemPoint = TMapPoint(locationData.lat, locationData.lon)

        val markerImage =
        BitmapFactory.decodeResource(
            context.resources,
            R.drawable.ic_marker_black
        )


        val marker = MarkerOverlay(
            tMapView,
            context,
            locationData.name
        )

        marker.id = locationData.name
        marker.icon = markerImage
        marker.setPosition(0.5F, 0.8F)
        marker.tMapPoint = markerItemPoint
        tMapView.addMarkerItem2(marker.id, marker)

    }
}
fun markMiddleLocation(tMapView: TMapView, context: Context, centerPoint: TMapPoint){
    val markerImage =
        BitmapFactory.decodeResource(
            context.resources,
            R.drawable.ic_marker_red
        )
    val marker = MarkerOverlay(tMapView, context, "중간지점")
    val strId = "중간지점"
    marker.id = strId
    marker.changeTextRedColor(context)
    marker.icon = markerImage
    marker.setPosition(0.5F, 1F)
    marker.tMapPoint = centerPoint
    tMapView.addMarkerItem2(strId, marker)
}


fun mapAutoZoom(tMapView: TMapView, locationList: ArrayList<LocationData>, centerPoint: TMapPoint) {
    var leftTopLat = centerPoint.latitude
    var leftTopLon = centerPoint.longitude
    var rightBottomLat = centerPoint.latitude
    var rightBottomLon = centerPoint.longitude
    try {
        for (i in 0 until locationList.size) {
            if (locationList.isNotEmpty()) {
                if (locationList[i].lat >= leftTopLat) {
                    leftTopLat = locationList[i].lat
                }
                if (locationList[i].lon >= leftTopLon) {
                    leftTopLon = locationList[i].lon
                }
                if (locationList[i].lat <= rightBottomLat) {
                    rightBottomLat = locationList[i].lat
                }

                if (locationList[i].lon <= rightBottomLon) {
                    rightBottomLon = locationList[i].lon
                }
            }

        }
        val leftTopPoint = TMapPoint(leftTopLat, leftTopLon)
        val rightBottomPoint = TMapPoint(rightBottomLat, rightBottomLon)
        tMapView.setCenterPoint(centerPoint.longitude, centerPoint.latitude)
        tMapView.zoomToTMapPoint(leftTopPoint, rightBottomPoint)
    } catch (e: Exception) {
        e.printStackTrace()
        tMapView.setCenterPoint(centerPoint.longitude, centerPoint.latitude)
    }

}
fun setMarkRatioLocation(tMapView: TMapView, context: Context, ratioPoint: TMapPoint) {

    val markerImage =
        BitmapFactory.decodeResource(
            context.resources,
            R.drawable.ic_marker_blue
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

fun getDefaultMarkerBackground(context: Context) = ContextCompat.getDrawable(context, R.drawable.ic_marker_gray)
fun getChangedtMarkerBackground(context: Context) = ContextCompat.getDrawable(context, R.drawable.ic_marker_gray5)