package org.first.dutchv11

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Color

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.skt.Tmap.*
import org.first.dutchv11.Data.LocationSetData

class MiddleLocationViewModel : ViewModel() {
    private var totalLatitude = 0.0
    private var totalLongitude = 0.0
    var middleLocationAddress = MutableLiveData<String>()
    var nearStationName = MutableLiveData<String>()
    private lateinit var centerPoint: TMapPoint

    fun calculateMiddleLocation() {
        for(i in 0 until LocationSetData.data.size){
            totalLatitude += LocationSetData.data[i].latitude
                totalLongitude += LocationSetData.data[i].longitude
        }

        centerPoint = TMapPoint(
            totalLatitude / LocationSetData.data.size,
            totalLongitude / LocationSetData.data.size
        )
        Log.e("centerPoint",centerPoint.toString())
        val tMapData = TMapData()
        try {
            middleLocationAddress.postValue(
                tMapData.convertGpsToAddress(
                    totalLatitude / LocationSetData.data.size,
                    totalLongitude / LocationSetData.data.size
                )
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun findNearSubway() {

        val stationData = TMapData()

        stationData.findAroundNamePOI(
            centerPoint,
            "지하철",
            20,
            3
        ) { p0 ->
            try {
                if (p0 == null) {
                    nearStationName.postValue("없음")
                } else {
                    nearStationName.postValue(p0[0].poiName)
                }
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }
    }

    fun markSearchLoaction(tMapView: TMapView, context: Context) {
        for (i in 0 until LocationSetData.data.size) {
            val markerItemPoint =
                TMapPoint(LocationSetData.data[i].latitude, LocationSetData.data[i].longitude)
            val markerImage =
                BitmapFactory.decodeResource(context.resources, R.drawable.markerblack)
            val markerItem = TMapMarkerItem()
            markerItem.icon = markerImage
            markerItem.canShowCallout = true
            markerItem.calloutTitle = LocationSetData.data[i].locationName
            markerItem.autoCalloutVisible = true
            markerItem.tMapPoint = markerItemPoint

            tMapView.addMarkerItem("markerItem$i", markerItem)


        }
    }

    fun markMiddleLocation(tMapView: TMapView, context: Context) {
        val markerItemPoint =
            TMapPoint(
                totalLatitude / LocationSetData.data.size,
                totalLongitude / LocationSetData.data.size
            )
        val markerImage =
            BitmapFactory.decodeResource(context.resources, R.drawable.markerred)
        val markerItem = TMapMarkerItem()
        markerItem.icon = markerImage
        markerItem.canShowCallout = true
        markerItem.calloutTitle = "중간지점"
        markerItem.autoCalloutVisible = true
        markerItem.tMapPoint = markerItemPoint
        tMapView.addMarkerItem("middlemarkerItem", markerItem)
    }

    fun setPolyLine(tMapView: TMapView) {
        for (i in 0 until LocationSetData.data.size) {
            val startPoint =
                TMapPoint(LocationSetData.data[i].latitude, LocationSetData.data[i].longitude)
            val tMapPolyLine = TMapData().findPathData(startPoint, centerPoint)
            tMapPolyLine.lineColor = Color.BLACK
            tMapPolyLine.lineWidth = 40F
            tMapView.addTMapPolyLine("Line$i", tMapPolyLine)
        }
    }

    fun mapAutoZoom(tMapView: TMapView) {
        var leftTopLat = centerPoint.latitude
        var leftTopLon = centerPoint.longitude
        var rightBottomLat = centerPoint.latitude
        var rightBottomLon = centerPoint.longitude
        try {
            for (i in 0 until LocationSetData.data.size) {
                if(LocationSetData.data.isNotEmpty()){
                    Log.e("dataLat", LocationSetData.data[i].latitude.toString())
                    Log.e("dataLon", LocationSetData.data[i].longitude.toString())
                    Log.e("dataCenter", centerPoint.toString())
                    if (LocationSetData.data[i].latitude >= leftTopLat) {
                        leftTopLat = LocationSetData.data[i].latitude
                    }
                    if (LocationSetData.data[i].longitude >= leftTopLon) {
                        leftTopLon = LocationSetData.data[i].longitude
                    }
                    if (LocationSetData.data[i].latitude <= rightBottomLat) {
                        rightBottomLat = LocationSetData.data[i].latitude
                    }

                    if (LocationSetData.data[i].longitude <= rightBottomLon) {
                        rightBottomLon = LocationSetData.data[i].longitude
                    }
                }

            }

            val leftTopPoint = TMapPoint(leftTopLat, leftTopLon)
            val rightBottomPoint = TMapPoint(rightBottomLat, rightBottomLon)

            tMapView.setCenterPoint(centerPoint.longitude, centerPoint.latitude)
            tMapView.zoomToTMapPoint(leftTopPoint, rightBottomPoint)
            Log.e("leftTopPoint",leftTopPoint.toString())
            Log.e("rightBottomPoint",rightBottomPoint.toString())
            Log.e("ZOOM", "done")
        } catch (e: Exception) {
            e.printStackTrace()
            tMapView.setCenterPoint(centerPoint.longitude, centerPoint.latitude)
        }

    }
}