package com.dutch2019.ui.middle

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Color
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dutch2019.MarkerOverlay
import com.dutch2019.R
import com.dutch2019.base.BaseViewModel
import com.dutch2019.model.LocationInfo
import com.dutch2019.ui.main.MainViewModel
import com.skt.Tmap.*


public class MiddleLocationViewModel : BaseViewModel() {

    private var locationlist = ArrayList<LocationInfo>()

    private var centerPoint = TMapPoint(0.0,0.0)

    private val _middleLocationAddress = MutableLiveData<String>()
    val middleLocationAddress : LiveData<String> get() = _middleLocationAddress

    private val _nearStationName = MutableLiveData<String>()
    val nearStationName : LiveData<String> get() = _nearStationName

    fun setLocationList(list : ArrayList<LocationInfo>){
        locationlist = list
    }

    fun getLocationList() : ArrayList<LocationInfo>{
        return locationlist
    }

    fun calculateCenterPoint(locationList : ArrayList<LocationInfo>) : TMapPoint {
        var totalLatitude = 0.0
        var totalLongitude = 0.0
        for (i in 0 until locationList.size) {
            totalLatitude += locationList[i].latitude
            totalLongitude += locationList[i].longitude
        }

        return TMapPoint(
            totalLatitude / locationList.size,
            totalLongitude / locationList.size
        )
    }

    fun setCenterPoint(point : TMapPoint){
        centerPoint = point
    }
    fun getCenterPoint() : TMapPoint = centerPoint

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
    fun setMiddleLocationAddress(address : String){
        _middleLocationAddress.value = address
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

        subwayName = if (tMapPOIItems.isEmpty()) {
            "없음"
        } else {
            tMapPOIItems[0].poiName
        }

        return subwayName

    }

    fun setNearSubway(name : String){
        _nearStationName.value = name
    }


    fun resetChangePoint(tMapView: TMapView) {
        tMapView.removeMarkerItem2("ratiomarkerItem")
    }


}
