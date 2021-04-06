package com.dutch2019.extension

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Color
import android.util.Log
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.lifecycle.ViewModel
import com.dutch2019.MarkerOverlay
import com.dutch2019.R
import com.dutch2019.base.BaseViewModel
import com.dutch2019.model.LocationInfo
import com.dutch2019.ui.main.MainViewModel
import com.dutch2019.ui.middle.MiddleLocationViewModel
import com.skt.Tmap.TMapData
import com.skt.Tmap.TMapPoint
import com.skt.Tmap.TMapView
import kotlinx.android.synthetic.main.fragment_middle_location.view.*
import java.lang.Exception

@BindingAdapter(value = ["mapview"])
fun mapview(layout: LinearLayout, viewModel: BaseViewModel) {
    var mainviewModel = viewModel as MiddleLocationViewModel
    var tMapView = TMapView(layout.context)
    layout.addView(tMapView)


//          서버값 가져오는 것들 rx or corutine처리
    viewModel.setCenterPoint(viewModel.calculateCenterPoint(viewModel.getLocationList()))
    var address = viewModel.getLocationAddress(viewModel.getCenterPoint())
//    var stationName = viewModel.findNearSubway(viewModel.getCenterPoint()) 서버사용
    viewModel.setMiddleLocationAddress(address)
//    viewModel.setNearSubway(stationName)
    markSearchLoaction(tMapView, layout.context, viewModel.getLocationList())
    markMiddleLocation(tMapView, layout.context, viewModel.getCenterPoint())
    setPolyLine(tMapView, viewModel.getLocationList(), viewModel.getCenterPoint())
    mapAutoZoom(tMapView, viewModel.getLocationList(), viewModel.getCenterPoint())


}

/*
private fun setBallonOverlayClickEvent(tMapView: TMapView, textView: TextView) {
    tMapView.setOnMarkerClickEvent { _, p1 ->
        object : Thread() {
            override fun run() {
                val point = p1.tMapPoint
                try {
                    when (p1.id) {
                        "ratiomarkerItem" -> {
                            viewModel.middleLocationAddress.value =
                                viewModel.getLocationAddress(point)
                            viewModel.searchNearFacilityPoint = point
                            viewModel.nearStationName.value = viewModel.findNearSubway(point)
                            textView.setTextColor(
                                ContextCompat.getColor(
                                    baseContext,
                                    R.color.blue
                                )
                            )
                            textView.text = "비율변경지점 결과"
                        }
                        "middlemarkerItem" -> {
                            viewModel.middleLocationAddress.value =
                                viewModel.getLocationAddress(point)

                            viewModel.searchNearFacilityPoint = point
                            viewModel.nearStationName.value = viewModel.findNearSubway(point)
                            textView.setTextColor(
                                ContextCompat.getColor(
                                    baseContext,
                                    R.color.orange
                                )
                            )
                            textView.text = "중간지점 결과"
                        }
                        else -> {
                            viewModel.middleLocationAddress.value =
                                viewModel.getLocationAddress(point)

                            viewModel.searchNearFacilityPoint = point
                            viewModel.nearStationName.value = viewModel.findNearSubway(point)
                            textView.setTextColor(
                                ContextCompat.getColor(
                                    baseContext,
                                    R.color.black
                                )
                            )
                            textView.text = "검색지점 결과"
                        }
                    }

                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }
        }.start()
    }
}
*/
fun markSearchLoaction(
    tMapView: TMapView,
    context: Context,
    locationList: ArrayList<LocationInfo>
) {

    if (locationList.isNotEmpty()) {
        for (i in 0 until locationList.size) {
            val markerItemPoint =
                TMapPoint(locationList[i].latitude, locationList[i].longitude)

            val markerImage =
                BitmapFactory.decodeResource(
                    context.resources,
                    R.drawable.result_ic_marker_black
                )


            val marker = MarkerOverlay(
                context,
                tMapView,
                locationList[i].name,
                "marker2$i"
            )
            var strId = "markerItem$i"
            marker.id = strId
            marker.icon = markerImage
            marker.setPosition(0.5F, 1F)
            marker.tMapPoint = markerItemPoint

            tMapView.addMarkerItem2(strId, marker)
        }
    }

}

fun markMiddleLocation(tMapView: TMapView, context: Context, centerPoint: TMapPoint) {
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
    tMapView.addMarkerItem2(strId, marker)

}

fun setPolyLine(tMapView: TMapView, locationList: ArrayList<LocationInfo>, centerPoint: TMapPoint) {
    try {
        for (i in 0 until locationList.size) {
            val startPoint =
                TMapPoint(locationList[i].latitude, locationList[i].longitude)
            val tMapPolyLine = TMapData().findPathData(startPoint, centerPoint)
            tMapPolyLine.lineColor = Color.BLACK
            tMapPolyLine.lineWidth = 16F
            tMapView.addTMapPolyLine("Line$i", tMapPolyLine)
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }

}

fun mapAutoZoom(tMapView: TMapView, locationList: ArrayList<LocationInfo>, centerPoint: TMapPoint) {
    var leftTopLat = centerPoint.latitude
    var leftTopLon = centerPoint.longitude
    var rightBottomLat = centerPoint.latitude
    var rightBottomLon = centerPoint.longitude
    try {
        for (i in 0 until locationList.size) {
            if (locationList.isNotEmpty()) {
                Log.e("dataLat", locationList[i].latitude.toString())
                Log.e("dataLon", locationList[i].longitude.toString())
                Log.e("dataCenter", centerPoint.toString())
                if (locationList[i].latitude >= leftTopLat) {
                    leftTopLat = locationList[i].latitude
                }
                if (locationList[i].longitude >= leftTopLon) {
                    leftTopLon = locationList[i].longitude
                }
                if (locationList[i].latitude <= rightBottomLat) {
                    rightBottomLat = locationList[i].latitude
                }

                if (locationList[i].longitude <= rightBottomLon) {
                    rightBottomLon = locationList[i].longitude
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

fun setMarkRatioLocation(tMapView: TMapView, context: Context, changePoint: TMapPoint) {
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