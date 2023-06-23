package com.dutch2019.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dutch2019.base.BaseViewModel
import com.dutch2019.model.LocationData
import com.dutch2019.model.MutableListLiveData

class MainViewModel : BaseViewModel() {

    private val _isConfirmedSktMapApikey = MutableLiveData<Boolean>(false)
    val isConfirmedSktMapApikey: LiveData<Boolean> get() = _isConfirmedSktMapApikey

    private val _locationList = MutableListLiveData<LocationData>()
    val locationList: LiveData<List<LocationData>> get() = _locationList

    init {
        createEmptyLeastLocationItems()
    }

    fun setConfirmedSktMapApikey() {
        _isConfirmedSktMapApikey.postValue(true)
    }

    fun isConfirmedSktMapApikey(): Boolean {
        return _isConfirmedSktMapApikey.value!!
    }

    fun addLocation(locationData: LocationData) {
        _locationList.add(locationData)
    }

    fun removeAtLocationList(position: Int){
        _locationList.remove(_locationList.value!![position])
    }
    fun setLocationList(list: ArrayList<LocationData>) {
        _locationList.value = list
    }

    fun getLocationList(): List<LocationData> {
        return _locationList.value!!
    }

    private fun createEmptyLeastLocationItems() {
        _locationList.add(LocationData())
        _locationList.add(LocationData())
        _locationList.add(LocationData())
    }

}