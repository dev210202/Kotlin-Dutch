package com.dutch2019.ui.middle

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dutch2019.base.BaseViewModel
import com.skt.Tmap.TMapPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import com.dutch2019.model.LocationData
import com.dutch2019.model.MutableListLiveData
import com.dutch2019.model.StartEndPointData
import com.dutch2019.repository.DBRepository
import com.dutch2019.repository.TMapRepository
import com.dutch2019.util.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MiddleViewModel @Inject constructor(
    private val tMapRepository: TMapRepository,
    private val dataBaseRepository: DBRepository,
) : BaseViewModel() {

    private var locationList = listOf<LocationData>()

    private var centerPoint = TMapPoint(0.0, 0.0)
    private var ratioPoint = TMapPoint(0.0, 0.0)
    private var searchPoint = TMapPoint(0.0, 0.0)

    private val _centerPointAddress = MutableLiveData<String>("")
    val centerPointAddress: LiveData<String> get() = _centerPointAddress

    private val _centerPointNearSubway = MutableLiveData<String>("")
    val centerPointNearSubway: LiveData<String> get() = _centerPointNearSubway

    private val _routeTime = MutableLiveData<String>("")
    val routeTime: LiveData<String> get() = _routeTime

    private val _facilityList = MutableListLiveData<LocationData>()
    val facilityList: LiveData<List<LocationData>> get() = _facilityList

    fun getLocationList(): List<LocationData> = locationList
    fun setLocationList(list: List<LocationData>) {
        locationList = list
    }

    fun getCenterPoint(): TMapPoint = centerPoint
    fun setCenterPoint(point: TMapPoint) {
        centerPoint = point
    }

    fun getRatioPoint(): TMapPoint = ratioPoint
    fun setRatioPoint(point: TMapPoint) {
        ratioPoint = point
    }

    fun getSearchPoint(): TMapPoint = searchPoint
    fun setSearchPoint(point: TMapPoint) {
        searchPoint = point
    }


    fun getIndexToFacilityList(item: TMapPoint, locationName: String): Int {
        val value = _facilityList.value!!.find {
            it.lat == item.latitude && it.lon == item.longitude && it.name == locationName
        }
        return _facilityList.value!!.indexOf(value)
    }


    fun setCenterPointNearSubway(point: TMapPoint) {
        viewModelScope.launch(Dispatchers.IO) {
            _centerPointNearSubway.postValue(tMapRepository.getNearSubway(point))
        }
    }

    fun setRouteTime(point: TMapPoint, latitude: Double, longitude: Double) {
        var startEndPointData =
            StartEndPointData(point.longitude, point.latitude, longitude, latitude)
        viewModelScope.launch(Dispatchers.IO) {
            _routeTime.postValue(
                convertTime(
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

    fun setCenterPointAddress(address: String) {
        _centerPointAddress.value = address
    }

    fun getCenterPointAddressValue(): String {
        return _centerPointAddress.value!!
    }

    fun searchCenterPointAddress(point: TMapPoint) {
        viewModelScope.launch(Dispatchers.IO) {
            _centerPointAddress.postValue(tMapRepository.getAddress(point))
        }
    }

    fun getFacilityList(): List<LocationData> = _facilityList.value!!


    fun searchNearFacility(point: TMapPoint, category: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val nearFacilityList = mutableListOf<LocationData>()
            val findList = tMapRepository.findNearFacility(point, category)
            if (findList.isNotNull()) {
                findList!!.forEach { item ->
                    if (isItemDataOK(item)) {
                        nearFacilityList.add(
                            LocationData(
                                0,
                                item.poiid,
                                item.poiName,
                                filtNull(item.poiAddress) + filtNull(" " + item.buildingNo1) + " " + filtNull(
                                    filtZero(" " + item.buildingNo2)
                                ),
                                filtNull(" " + item.telNo),
                                item.poiPoint.latitude,
                                item.poiPoint.longitude
                            )
                        )
                    }
                }
                _facilityList.postValue(nearFacilityList)
            } else {
                _facilityList.postValue(listOf())
            }
        }
    }
}