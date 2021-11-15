package com.dutch2019.ui.middle

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dutch2019.base.BaseViewModel
import com.skt.Tmap.TMapPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import com.dutch2019.model.LocationDBData
import com.dutch2019.model.LocationData
import com.dutch2019.model.StartEndPointData
import com.dutch2019.repository.DBRepository
import com.dutch2019.repository.SafeCasterRepository
import com.dutch2019.repository.TMapRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MiddleViewModel @Inject constructor(
    private val tMapRepository: TMapRepository,
    private val dataBaseRepository: DBRepository,
    private val safeCasterRepository: SafeCasterRepository
) : BaseViewModel() {

    private var locationList = ArrayList<LocationData>()

    private var centerPoint = TMapPoint(0.0, 0.0)
    private var ratioPoint = TMapPoint(0.0, 0.0)

    private val _centerPointAddress = MutableLiveData<String>()
    val centerPointAddress: LiveData<String> get() = _centerPointAddress

    private val _centerPointNearSubway = MutableLiveData<String>()
    val centerPointNearSubway: LiveData<String> get() = _centerPointNearSubway

    private val _routeTime = MutableLiveData<String>()
    val routeTime: LiveData<String> get() = _routeTime

    private val _zipCode = MutableLiveData<String>()
    val zipCode: LiveData<String> get() = _zipCode

    private val _safetyIndex = MutableLiveData<Double>()
    val safetyIndex: LiveData<Double> get() = _safetyIndex

    private val _ratio = MutableLiveData<String>("5 : 5")
    val ratio: LiveData<String> get() = _ratio

    fun setLocationList(list: ArrayList<LocationData>) {
        locationList = list
    }

    fun getLocationList(): ArrayList<LocationData> {
        return locationList
    }

    fun calculateCenterPoint(locationList: ArrayList<LocationData>): TMapPoint {
        var totalLat = 0.0
        var totalLon = 0.0
        locationList.forEach { data ->
            totalLat += data.lat
            totalLon += data.lon
        }
        return TMapPoint(
            totalLat / locationList.size,
            totalLon / locationList.size
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


    fun setCenterPointAddress(point: TMapPoint) {
        viewModelScope.launch(Dispatchers.IO) {
            _centerPointAddress.postValue(tMapRepository.getAddress(point))

        }
    }

    fun setCenterPointNearSubway(point: TMapPoint) {
        viewModelScope.launch(Dispatchers.IO) {
            _centerPointNearSubway.postValue(tMapRepository.getNearSubway(point))
        }
    }

    fun setRouteTime(point: TMapPoint, latitude: Double, longitude: Double) {
        var startEndPointData = StartEndPointData(
            point.longitude,
            point.latitude,
            longitude,
            latitude
        )
        viewModelScope.launch(Dispatchers.IO) {
            _routeTime.postValue(
                "소요시간 : " + convertTime(
                    tMapRepository.getRouteTime(
                        startEndPointData
                    )
                )
            )
        }
    }

    fun resetRouteTime() {
        _routeTime.value = ""
    }

    fun saveLocations(locationList: ArrayList<LocationData>) {
        val data = LocationDBData(
            0,
            centerPoint.latitude,
            centerPoint.longitude,
            _centerPointAddress.value!!,
            locationList
        )
        Log.i("data", data.toString())
        viewModelScope.launch(Dispatchers.IO) {
            dataBaseRepository.insertRecentData(data)
        }
    }

    fun loadZipCode(address: String) {
        Log.i("ADDRESS!!", address)
        viewModelScope.launch(Dispatchers.IO) {
            _zipCode.postValue(getZipCode(address))
        }
    }

    fun loadSafetyIndex(zipCode: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _safetyIndex.postValue(getSafetyIndex(zipCode))
        }
    }

    private suspend fun getSafetyIndex(zipCode: String): Double {
        return safeCasterRepository.getSafetyIndex(zipCode)
    }

    private suspend fun getZipCode(address: String): String {
        return tMapRepository.getZipCode(address)
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

    fun getCalculatedRatioPoint(point1: TMapPoint, point2: TMapPoint): TMapPoint? {
        var changePoint = TMapPoint(0.0, 0.0)
        var ratioValue = Integer.valueOf(ratio.value?.split(" : ")?.get(0))
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
}