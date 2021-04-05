package com.dutch2019.ui.main

import android.content.Context
import android.graphics.BitmapFactory
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dutch2019.R
import com.dutch2019.base.BaseViewModel
import com.dutch2019.model.LocationInfo
import com.skt.Tmap.TMapData
import com.skt.Tmap.TMapPoint
import com.skt.Tmap.TMapView

open class MainViewModel : BaseViewModel() {
    private val list = ArrayList<LocationInfo>()

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
                    list.size,
                    "위치를 입력해주세요" + list.size,
                    "",
                    0.0,
                    0.0
                )
            )
        }
    }

    fun setCheckLocationInfo(locationInfo: LocationInfo) {
        _checkLocationInfo.value = locationInfo
    }




}