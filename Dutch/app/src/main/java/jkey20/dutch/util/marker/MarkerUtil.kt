package jkey20.dutch.util.marker

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.drawable.VectorDrawable
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toBitmap
import com.skt.Tmap.TMapPoint
import com.skt.Tmap.TMapView
import jkey20.dutch.R
import jkey20.dutch.model.LocationData
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
        marker.setPosition(0.5F, 1F)
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
