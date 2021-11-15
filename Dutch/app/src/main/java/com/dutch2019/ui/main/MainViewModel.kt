package com.dutch2019.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dutch2019.base.BaseViewModel
import com.dutch2019.model.LocationData

class MainViewModel : BaseViewModel() {
    private val _checkLocationList = MutableLiveData(ArrayList<LocationData>())
    val checkLocationList : LiveData<ArrayList<LocationData>> get() = _checkLocationList

    fun addLocation(locationData: LocationData){
        _checkLocationList.value!!.add(locationData)
    }

    fun setCheckLocationList(list : ArrayList<LocationData>){
        _checkLocationList.value = list
    }
}