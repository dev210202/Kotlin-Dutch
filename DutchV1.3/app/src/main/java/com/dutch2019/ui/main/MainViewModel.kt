package com.dutch2019.ui.main

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dutch2019.base.BaseViewModel
import com.dutch2019.model.LocationInfo
import com.dutch2019.repository.LocationRepository
import com.skt.Tmap.TMapView
import kotlinx.coroutines.launch

open class MainViewModel : BaseViewModel() {
    private val list = ArrayList<LocationInfo>()
    private var id = 0

    private var locationRepository = LocationRepository()

    private val _dynamicButtonData = MutableLiveData<ArrayList<LocationInfo>>(list)
    val dynamicButtonData: LiveData<ArrayList<LocationInfo>> get() = _dynamicButtonData

    private val _checkLocationInfo = MutableLiveData<LocationInfo>()
    val checkLocationInfo: LiveData<LocationInfo> get() = _checkLocationInfo

    fun addDynamicButtonData(locationInfo: LocationInfo) {
        _dynamicButtonData.value = _dynamicButtonData.value?.apply {
            add(locationInfo)
        }
    }

    fun replaceDynamicButtonData(locationPosition : Int, locationInfo: LocationInfo){
        _dynamicButtonData.value?.set(locationPosition, locationInfo)
    }

    fun addDummyLocationData() {
        _dynamicButtonData.value = _dynamicButtonData.value?.apply {
            add(
                LocationInfo(
                    list.size
                )
            )
        }
    }

    fun setCheckLocationInfo(locationInfo: LocationInfo) {
        _checkLocationInfo.value = locationInfo
    }

    fun initDB(application : Application){
        viewModelScope.launch {
            locationRepository.setRecentDB(application)
        }
    }

    fun insertDataInDB(locationInfo: LocationInfo){
        viewModelScope.launch {
            locationRepository.insertRecentData(locationInfo)
        }
    }

    fun getRecentDataInDB(): List<LocationInfo> {
        return locationRepository.getRecentLocationListData()
    }

}