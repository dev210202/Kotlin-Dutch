package com.dutch2019.ui.middle

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dutch2019.base.BaseViewModel
import com.dutch2019.model.LocationInfo
import com.dutch2019.model.StartEndPointData
import com.dutch2019.repository.APIRepository
import com.skt.Tmap.TMapData
import com.skt.Tmap.TMapPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


class MiddleLocationViewModel : BaseViewModel() {

    private var locationlist = ArrayList<LocationInfo>()

    private var centerPoint = TMapPoint(0.0, 0.0)
    private var ratioPoint = TMapPoint(0.0, 0.0)

    private val _middleLocationAddress = MutableLiveData<String>()
    val middleLocationAddress: LiveData<String> get() = _middleLocationAddress

    private val _nearStationName = MutableLiveData<String>()
    val nearStationName: LiveData<String> get() = _nearStationName

    private val _totalRouteTime = MutableLiveData<String>()
    val totalRouteTime: LiveData<String> get() = _totalRouteTime

    private val apiRepository = APIRepository.getInstance()

    private val _ratio = MutableLiveData<String>("5:5")
    val ratio: LiveData<String> get() = _ratio


    fun getMiddleRouteTime(centerPoint: TMapPoint, latitude: Double, longitude: Double) {
        var startEndPointData = StartEndPointData(
            centerPoint.longitude,
            centerPoint.latitude,
            longitude,
            latitude
        )
        compositeDisposable.add(apiRepository.getRouteTime(startEndPointData).subscribe({ data ->
            var totalTime = data.features[0].properties.totalTime
            _totalRouteTime.postValue(convertTime(totalTime))
        }, { error ->
            Log.e("data error", error.message.toString())
        }))
    }

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

    fun setRatioPoint(point: TMapPoint) {
        ratioPoint = point
    }
    fun getRatioPoint(): TMapPoint = ratioPoint

    fun resetRatioPoint(){
        ratioPoint = TMapPoint(0.0, 0.0)
    }

    fun setLocationAddress(point: TMapPoint) {
        val tMapData = TMapData()
        var locationAddress: String

        CoroutineScope(Dispatchers.IO).launch {
            locationAddress = try {
                tMapData.convertGpsToAddress(point.latitude, point.longitude)
            } catch (e: Exception) {
                "상세주소가 없습니다."
            }
            async(Dispatchers.Main) {
                _middleLocationAddress.value = locationAddress
            }
        }
    }

    fun setNearSubway(point: TMapPoint) {

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
            }
            else {
                tMapPOIItems[0].poiName
            }

            async(Dispatchers.Main) {
                _nearStationName.value = subwayName
            }
        }
    }

    private fun convertTime(time: String): String {
        var result = ""
        var totalTime = time.toInt()
        if (totalTime >= 3600) {
            var hour = totalTime / 3600
            totalTime %= 3600
            result = hour.toString() + "시간"
        }
        if (totalTime >= 60) {
            var minute = totalTime / 60
            totalTime %= 60
            result += minute.toString() + "분"
        }
        if (totalTime > 0) {
            result += totalTime.toString() + "초"
        }
        return result
    }

    fun setRatio(value: String) {
        _ratio.postValue(value)
    }

    fun getRatioPoint(point1: TMapPoint, point2: TMapPoint): TMapPoint? {
        var changePoint = TMapPoint(0.0, 0.0)
        var ratioValue = Integer.valueOf(ratio.value?.split(":")?.get(0))
        when {

            (ratioValue ==  5) -> {
                changePoint = TMapPoint(
                    (point1.latitude + point2.latitude) / 2,
                    (point1.longitude + point2.longitude) / 2
                )
                Log.e("0", "!!")
            }

            is1stQuadrant(point1, point2) -> {

                Log.e("1", "!!")
                var changeLat = ((10 - ratioValue) * point1.latitude + (ratioValue) * point2.latitude) / (10)
                var changeLon = ((10 - ratioValue) * point1.longitude + ratioValue * point2.longitude) / (10)
                changePoint = TMapPoint(changeLat, changeLon)
            }
            is2ndQuadrant(point1, point2) -> {

                Log.e("2", "!!")
                var changeLat = ((10 - ratioValue) * point1.latitude + ratioValue * point2.latitude) / (10)
                var changeLon = (ratioValue * point2.longitude + (10 - ratioValue) * point1.longitude) / (10)
                changePoint = TMapPoint(changeLat, changeLon)
            }
            is3rdQuadrant(point1, point2) -> {

                Log.e("3", "!!")
                var changeLat = (ratioValue * point2.latitude + (10 - ratioValue) * point1.latitude) / (10)
                var changeLon = (ratioValue * point2.longitude + (10 - ratioValue) * point1.longitude) / (10)
                changePoint = TMapPoint(changeLat, changeLon)
            }
            is4thQuadrant(point1, point2) -> {

                Log.e("4", "!!")
                var changeLat = (ratioValue * point2.latitude + (10 - ratioValue) * point1.latitude) / (10)
                var changeLon = ((10 - ratioValue) * point1.longitude + ratioValue * point2.longitude) / (10)
                changePoint = TMapPoint(changeLat, changeLon)
            }
        }
        return changePoint
    }


    private fun is1stQuadrant(point1: TMapPoint, point2: TMapPoint): Boolean {
        if (point1.latitude < point2.latitude && point1.longitude < point2.longitude) {
            return true
        }
        return false
    }

    private fun is2ndQuadrant(point1: TMapPoint, point2: TMapPoint): Boolean {
        if (point1.latitude > point2.latitude && point1.longitude < point2.longitude) {
            return true
        }
        return false
    }

    private fun is3rdQuadrant(point1: TMapPoint, point2: TMapPoint): Boolean {
        if (point1.latitude > point2.latitude && point1.longitude > point2.longitude) {
            return true
        }
        return false
    }

    private fun is4thQuadrant(point1: TMapPoint, point2: TMapPoint): Boolean {
        if (point1.latitude < point2.latitude && point1.longitude > point2.longitude) {
            return true
        }
        return false
    }

    /*
      fun resetChangePoint(tMapView: TMapView) {
          tMapView.removeMarkerItem2("ratiomarkerItem")
      }
  */
    fun resetRouteTime() {
        _totalRouteTime.postValue(" ")
    }
}
