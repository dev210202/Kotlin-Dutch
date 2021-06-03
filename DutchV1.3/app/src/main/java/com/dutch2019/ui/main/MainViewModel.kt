package com.dutch2019.ui.main

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dutch2019.base.BaseViewModel
import com.dutch2019.model.LocationDataDB
import com.dutch2019.model.LocationInfo
import com.dutch2019.repository.DBRepository
import kotlinx.coroutines.launch

open class MainViewModel : BaseViewModel() {
    private val list = ArrayList<LocationInfo>()
    private var id = 0

    private var locationRepository = DBRepository()

    private val _dynamicButtonData = MutableLiveData<ArrayList<LocationInfo>>(list)
    val dynamicButtonData: LiveData<ArrayList<LocationInfo>> get() = _dynamicButtonData

    private val _checkLocationInfo = MutableLiveData<LocationInfo>()
    val checkLocationInfo: LiveData<LocationInfo> get() = _checkLocationInfo

    fun addDynamicButtonData(locationInfo: LocationInfo) {
        _dynamicButtonData.value = _dynamicButtonData.value?.apply {
            add(locationInfo)
        }
    }

    fun replaceDynamicButtonData(locationPosition: Int, locationInfo: LocationInfo) {
        for (i in 0 until _dynamicButtonData.value?.size!!) {
            if (_dynamicButtonData.value!![i].id == locationPosition) {
                _dynamicButtonData.value!![i] = locationInfo
            }
        }
    }

    fun clearDynamicButtonData(){
        _dynamicButtonData.value?.clear()
    }

    fun addDummyLocationData() {
        _dynamicButtonData.value = _dynamicButtonData.value?.apply {
            add(LocationInfo(id++))
        }
    }

    fun setCheckLocationInfo(locationInfo: LocationInfo) {
        _checkLocationInfo.value = locationInfo
    }

    fun initDB(application: Application) {
        viewModelScope.launch {
            locationRepository.setRecentDB(application)
        }
    }

    fun insertDataInDB(locationData: LocationDataDB) {
        viewModelScope.launch {
            locationRepository.insertRecentData(locationData)
        }
    }


}