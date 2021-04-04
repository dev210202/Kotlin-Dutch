package com.dutch2019.extension

import android.graphics.BitmapFactory
import android.widget.LinearLayout
import androidx.databinding.BindingAdapter
import com.dutch2019.R
import com.dutch2019.model.LocationInfo
import com.skt.Tmap.TMapMarkerItem
import com.skt.Tmap.TMapPoint
import com.skt.Tmap.TMapView

@BindingAdapter(value = ["mapsetting"])
fun mapSetting(mapLayout: LinearLayout, locationInfo: LocationInfo) {
    val markerItemPoint = TMapPoint(locationInfo.latitude, locationInfo.longitude)
    val markerImage = BitmapFactory.decodeResource(
        mapLayout.context.resources,
        R.drawable.result_ic_marker_black
    )
    val markerItem = TMapMarkerItem().apply {
        icon = markerImage
        tMapPoint = markerItemPoint
        setPosition(0.5F, 1F)
    }
    var tMapView = TMapView(mapLayout.context).apply {
        setSKTMapApiKey("l7xx75e02f3eccaa4f56b3f269cb4a9f2b43")
        setCenterPoint(locationInfo.longitude, locationInfo.latitude)
        addMarkerItem("markerItem", markerItem)
    }
    mapLayout.addView(tMapView)
}