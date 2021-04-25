package com.dutch2019.ui.middle

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dutch2019.base.BaseViewModel
import com.dutch2019.model.LocationInfo
import com.skt.Tmap.TMapData
import com.skt.Tmap.TMapPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


class MiddleLocationViewModel : BaseViewModel() {

    private var locationlist = ArrayList<LocationInfo>()

    private var centerPoint = TMapPoint(0.0, 0.0)

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
        var locationAddress: String


        CoroutineScope(Dispatchers.IO).launch {
            locationAddress = try {
                tMapData.convertGpsToAddress(point.latitude, point.longitude)
            } catch (e : Exception){
                "상세주소가 없습니다."
            }
            async(Dispatchers.Main) {
                _middleLocationAddress.value = locationAddress
            }
        }
    }

    fun setNearSubway(point: TMapPoint){

        val stationData = TMapData()
        var subwayName: String

        CoroutineScope(Dispatchers.IO).launch {
            val tMapPOIItems = stationData.findAroundNamePOI(
                point,
                "지하철",
                20,
                3
            )
            subwayName = if (tMapPOIItems.isEmpty()) {
                "근처 지하철이 없습니다."
            } else {
                tMapPOIItems[0].poiName
            }

            async(Dispatchers.Main) {
                _nearStationName.value = subwayName
            }
        }
    }
/*
    fun resetChangePoint(tMapView: TMapView) {
        tMapView.removeMarkerItem2("ratiomarkerItem")
    }
*/

}
