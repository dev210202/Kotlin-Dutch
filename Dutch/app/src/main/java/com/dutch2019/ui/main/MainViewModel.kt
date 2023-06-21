package com.dutch2019.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dutch2019.base.BaseViewModel
import com.dutch2019.model.LocationData

class MainViewModel : BaseViewModel() {

    private val _infoMessage = MutableLiveData<String>()
    val infoMessage: LiveData<String> get() = _infoMessage

    private val _sktMapApikeyAuth = MutableLiveData<Boolean>(false)
    val sktMapApikeyAuth: LiveData<Boolean> get() = _sktMapApikeyAuth

    private val _checkLocationList = MutableLiveData(ArrayList<LocationData>())
    val checkLocationList: LiveData<ArrayList<LocationData>> get() = _checkLocationList

    fun setInfoMessage(message: String) {
        _infoMessage.postValue(message)
    }

    fun setSKTMapApikeySuccess() {
        _sktMapApikeyAuth.postValue(true)
    }

    fun setSKTMapApikeyFail() {
        _sktMapApikeyAuth.postValue(false)
    }

    fun isSKTMapApikeySuccess(): Boolean {
        return _sktMapApikeyAuth.value!!
    }

    fun addLocation(locationData: LocationData) {
        _checkLocationList.value!!.add(locationData)
    }

    fun setCheckLocationList(list: ArrayList<LocationData>) {
        _checkLocationList.value = list
    }
}