package com.dutch2019.ui.middle

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.dutch2019.base.BaseViewModel
import com.dutch2019.model.LocationInfo
import com.skt.Tmap.TMapData
import com.skt.Tmap.TMapPoint
import com.skt.Tmap.TMapView
import kotlinx.coroutines.*


public class MiddleLocationViewModel : BaseViewModel() {

    private var locationlist = ArrayList<LocationInfo>()

    private var centerPoint = TMapPoint(0.0, 0.0)
    private var centerAddress = ""

    private val _middleLocationAddress = MutableLiveData<String>()
    val middleLocationAddress: LiveData<String> get() = _middleLocationAddress

    private val _nearStationName = MutableLiveData<String>()
    val nearStationName: LiveData<String> get() = _nearStationName

    fun setLocationList(list: ArrayList<LocationInfo>) {
        locationlist = list
    }

    fun getLocationList(): ArrayList<LocationInfo> {
        return locationlist
    }

    fun calculateCenterPoint(locationList: ArrayList<LocationInfo>): TMapPoint {
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

    fun setCenterPoint(point: TMapPoint) {
        centerPoint = point
    }

    fun getCenterPoint(): TMapPoint = centerPoint

    fun setLocationAddress(point: TMapPoint) {
        val tMapData = TMapData()
        var locationAddress = ""


        CoroutineScope(Dispatchers.IO).launch {
            locationAddress = tMapData.convertGpsToAddress(point.latitude, point.longitude)
            async(Dispatchers.Main) {
                _middleLocationAddress.value = locationAddress
            }
        }
    }

    fun setNearSubway(point: TMapPoint){

        val stationData = TMapData()
        var subwayName = ""

        CoroutineScope(Dispatchers.IO).launch {
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

            async(Dispatchers.Main) {
                _nearStationName.value = subwayName
            }
        }
    }

    fun resetChangePoint(tMapView: TMapView) {
        tMapView.removeMarkerItem2("ratiomarkerItem")
    }


}
