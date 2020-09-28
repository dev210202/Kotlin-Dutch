package com.dutch2019.ui.middle

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.PointF
import android.util.Log
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dutch2019.data.LocationSetData
import com.dutch2019.MarkerOverlay
import com.dutch2019.R
import com.skt.Tmap.*
import java.util.ArrayList


public class MiddleLocationViewModel : ViewModel() {
    private var totalLatitude = 0.0
    private var totalLongitude = 0.0
    var middleLocationAddress = MutableLiveData<String>()
    var nearStationName = MutableLiveData<String>()
    lateinit var centerPoint: TMapPoint
    var searchNearFacilityPoint = TMapPoint(0.0, 0.0)
    var changePoint: TMapPoint? = null

    fun calculateCenterPoint() {
        for (i in 0 until LocationSetData.data.size) {
            totalLatitude += LocationSetData.data[i].latitude
            totalLongitude += LocationSetData.data[i].longitude
        }

        centerPoint = TMapPoint(
            totalLatitude / LocationSetData.data.size,
            totalLongitude / LocationSetData.data.size
        )
        searchNearFacilityPoint = centerPoint

        Log.e("centerPoint", centerPoint.toString())


    }

    fun findNearSubway(point: TMapPoint): String {

        val stationData = TMapData()
        var subwayName = ""
        var tMapPOIItems = stationData.findAroundNamePOI(
            point,
            "지하철",
            20,
            3
        )

        if (tMapPOIItems.isEmpty()) {
            subwayName = "없음"
        } else {
            subwayName = tMapPOIItems[0].poiName
        }

        return subwayName

    }

    fun markSearchLoaction(tMapView: TMapView, context: Context) {
        for (i in 0 until LocationSetData.data.size) {
            val markerItemPoint =
                TMapPoint(LocationSetData.data[i].latitude, LocationSetData.data[i].longitude)

            val markerImage =
                BitmapFactory.decodeResource(
                    context.resources,
                    R.drawable.result_ic_marker_black
                )


            val marker = MarkerOverlay(
                context,
                tMapView,
                LocationSetData.data[i].locationName,
                "marker2" + i
            )
            var strId = "markerItem" + i
            marker.id = strId
            marker.icon = markerImage
            marker.setPosition(0.5F, 1F)
            marker.tMapPoint = markerItemPoint

            tMapView.setOnMarkerClickEvent(TMapView.OnCalloutMarker2ClickCallback { id, tMapMarkerItem2 ->

            })
            tMapView.addMarkerItem2(strId, marker)


        }
    }

    fun markMiddleLocation(tMapView: TMapView, context: Context) {
        val markerItemPoint =
            centerPoint
        val markerImage =
            BitmapFactory.decodeResource(
                context.resources,
                R.drawable.group6
            )


        val marker = MarkerOverlay(context, tMapView, "중간지점", "middlemarkerItem")
        var strId = "middlemarkerItem"
        marker.id = strId
        marker.changeTextRedColor(context)
        marker.icon = markerImage
        marker.setPosition(0.5F, 1F)
        marker.tMapPoint = markerItemPoint

        Log.e("MARKERPOINTRED", markerItemPoint.toString())
        tMapView.addMarkerItem2(strId, marker)

    }

    fun setPolyLine(tMapView: TMapView) {
        try {
            for (i in 0 until LocationSetData.data.size) {
                val startPoint =
                    TMapPoint(LocationSetData.data[i].latitude, LocationSetData.data[i].longitude)
                val tMapPolyLine = TMapData().findPathData(startPoint, centerPoint)
                tMapPolyLine.lineColor = Color.BLACK
                tMapPolyLine.lineWidth = 16F
                tMapView.addTMapPolyLine("Line$i", tMapPolyLine)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun mapAutoZoom(tMapView: TMapView) {
        var leftTopLat = centerPoint.latitude
        var leftTopLon = centerPoint.longitude
        var rightBottomLat = centerPoint.latitude
        var rightBottomLon = centerPoint.longitude
        try {
            for (i in 0 until LocationSetData.data.size) {
                if (LocationSetData.data.isNotEmpty()) {
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
            Log.e("leftTopPoint", leftTopPoint.toString())
            Log.e("rightBottomPoint", rightBottomPoint.toString())
            Log.e("ZOOM", "done")
        } catch (e: Exception) {
            e.printStackTrace()
            tMapView.setCenterPoint(centerPoint.longitude, centerPoint.latitude)
        }

    }

    fun setChangePoint(point1: TMapPoint, point2: TMapPoint, ratio: Int): TMapPoint? {

        when {

            (ratio == 5) -> {
                changePoint = TMapPoint(
                    (point1.latitude + point2.latitude) / 2,
                    (point1.longitude + point2.longitude) / 2
                )
                Log.e("0", "!!")
            }

            is1stQuadrant(point1, point2) -> {

                Log.e("1", "!!")
                var changeLat = ((10 - ratio) * point1.latitude + (ratio) * point2.latitude) / (10)
                var changeLon = ((10 - ratio) * point1.longitude + ratio * point2.longitude) / (10)
                changePoint = TMapPoint(changeLat, changeLon)
            }
            is2ndQuadrant(point1, point2) -> {

                Log.e("2", "!!")
                var changeLat = ((10 - ratio) * point1.latitude + ratio * point2.latitude) / (10)
                var changeLon = (ratio * point2.longitude + (10 - ratio) * point1.longitude) / (10)
                changePoint = TMapPoint(changeLat, changeLon)
            }
            is3rdQuadrant(point1, point2) -> {

                Log.e("3", "!!")
                var changeLat = (ratio * point2.latitude + (10 - ratio) * point1.latitude) / (10)
                var changeLon = (ratio * point2.longitude + (10 - ratio) * point1.longitude) / (10)
                changePoint = TMapPoint(changeLat, changeLon)
            }
            is4thQuadrant(point1, point2) -> {

                Log.e("4", "!!")
                var changeLat = (ratio * point2.latitude + (10 - ratio) * point1.latitude) / (10)
                var changeLon = ((10 - ratio) * point1.longitude + ratio * point2.longitude) / (10)
                changePoint = TMapPoint(changeLat, changeLon)
            }
        }
        return changePoint
    }


    fun is1stQuadrant(point1: TMapPoint, point2: TMapPoint): Boolean {
        if (point1.latitude < point2.latitude && point1.longitude < point2.longitude) {
            return true
        }
        return false
    }

    fun is2ndQuadrant(point1: TMapPoint, point2: TMapPoint): Boolean {
        if (point1.latitude > point2.latitude && point1.longitude < point2.longitude) {
            return true
        }
        return false
    }

    fun is3rdQuadrant(point1: TMapPoint, point2: TMapPoint): Boolean {
        if (point1.latitude > point2.latitude && point1.longitude > point2.longitude) {
            return true
        }
        return false
    }

    fun is4thQuadrant(point1: TMapPoint, point2: TMapPoint): Boolean {
        if (point1.latitude < point2.latitude && point1.longitude > point2.longitude) {
            return true
        }
        return false
    }

    fun getLocationAddress(point: TMapPoint): String {
        val tMapData = TMapData()
        var locationAddress = ""
        try {
            locationAddress =
                tMapData.convertGpsToAddress(
                    point.latitude, point.longitude
                )

        } catch (e: Exception) {
            e.printStackTrace()
        }
        return locationAddress
    }

    fun setMarkRatioLocation(changePoint: TMapPoint, tMapView: TMapView, context: Context) {
        val markerImage =
            BitmapFactory.decodeResource(
                context.resources,
                R.drawable.result_ic_maker_blue
            )


        val marker = MarkerOverlay(context, tMapView, "비율변경지점", "ratioMarkerItem")

        var strId = "ratiomarkerItem"
        marker.id = strId
        marker.chagneTextBlueColor(context)
        marker.icon = markerImage
        marker.setPosition(0.5F, 1F)
        marker.tMapPoint = changePoint
        Log.e("MARKERPOINTBLUE", changePoint.toString())
        tMapView.addMarkerItem2(strId, marker)
        marker.markerTouch
    }

    fun resetChangePoint(tMapView: TMapView) {
        tMapView.removeMarkerItem2("ratiomarkerItem")
    }


}
