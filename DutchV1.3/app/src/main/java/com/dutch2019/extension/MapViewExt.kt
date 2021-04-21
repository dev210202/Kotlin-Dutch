package com.dutch2019.extension

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Color
import android.util.Log
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.dutch2019.MarkerOverlay
import com.dutch2019.R
import com.dutch2019.base.BaseViewModel
import com.dutch2019.model.LocationInfo
import com.dutch2019.ui.middle.MiddleLocationViewModel
import com.skt.Tmap.TMapData
import com.skt.Tmap.TMapPoint
import com.skt.Tmap.TMapView
import kotlinx.android.synthetic.main.fragment_middle_location.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

@BindingAdapter(value = ["mapview"])
fun mapview(layout: LinearLayout, viewModel: BaseViewModel) {
    val mainViewModel = viewModel as MiddleLocationViewModel
    val tMapView = TMapView(layout.context)
    layout.addView(tMapView)

    mainViewModel.setCenterPoint(mainViewModel.calculateCenterPoint(mainViewModel.getLocationList()))

    markSearchLoaction(tMapView, layout.context, mainViewModel.getLocationList())
    markMiddleLocation(tMapView, layout.context, mainViewModel.getCenterPoint())
    setBallonOverlayClickEvent(tMapView, viewModel)
    CoroutineScope(Dispatchers.IO).launch {
        setPolyLine(tMapView, mainViewModel.getLocationList(), mainViewModel.getCenterPoint())
        mainViewModel.setLocationAddress(mainViewModel.getCenterPoint())
        mainViewModel.setNearSubway(mainViewModel.getCenterPoint())
    }
    mapAutoZoom(tMapView, mainViewModel.getLocationList(), mainViewModel.getCenterPoint())
}

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
            val strId = locationList[i].name
            marker.id = strId
            marker.icon = markerImage
            marker.setPosition(0.5F, 1F)
            marker.tMapPoint = markerItemPoint

            tMapView.addMarkerItem2(strId, marker)
        }
    }

}

fun markMiddleLocation(tMapView: TMapView, context: Context, centerPoint: TMapPoint) {
    val markerImage =
        BitmapFactory.decodeResource(
            context.resources,
            R.drawable.group6
        )
    val marker = MarkerOverlay(context, tMapView, "중간지점", "middlemarkerItem")
    val strId = "중간지점"
    marker.id = strId
    marker.changeTextRedColor(context)
    marker.icon = markerImage
    marker.setPosition(0.5F, 1F)
    marker.tMapPoint = centerPoint
    tMapView.addMarkerItem2(strId, marker)
}

fun setPolyLine(
    tMapView: TMapView,
    locationList: ArrayList<LocationInfo>,
    centerPoint: TMapPoint
) {
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
    } catch (e: Exception) {
        e.printStackTrace()
        tMapView.setCenterPoint(centerPoint.longitude, centerPoint.latitude)
    }

}

fun setBallonOverlayClickEvent(tMapView: TMapView, viewModel: BaseViewModel) {
    tMapView.setOnMarkerClickEvent { _, p1 ->
        val point = p1.tMapPoint
        (viewModel as MiddleLocationViewModel).setCenterPoint(point)
        viewModel.setLocationAddress(viewModel.getCenterPoint())
        viewModel.setNearSubway(viewModel.getCenterPoint())
        tMapView.rootView.middlelocationresult_textview.text = p1.id
        if (p1.id == "중간지점") {
            tMapView.rootView.middlelocationresult_textview.setTextColor(
                ContextCompat.getColor(tMapView.rootView.context, R.color.orange)
            )
        } else {
            tMapView.rootView.middlelocationresult_textview.setTextColor(
                ContextCompat.getColor(tMapView.rootView.context, R.color.black)
            )
        }
    }

}

/*
fun setMarkRatioLocation(tMapView: TMapView, context: Context, changePoint: TMapPoint) {
    val markerImage =
        BitmapFactory.decodeResource(
            context.resources,
            R.drawable.result_ic_maker_blue
        )


    val marker = MarkerOverlay(context, tMapView, "비율변경지점", "ratioMarkerItem")

    val strId = "ratiomarkerItem"
    marker.id = strId
    marker.chagneTextBlueColor(context)
    marker.icon = markerImage
    marker.setPosition(0.5F, 1F)
    marker.tMapPoint = changePoint
    Log.e("MARKERPOINTBLUE", changePoint.toString())
    tMapView.addMarkerItem2(strId, marker)
    marker.markerTouch
}
*/