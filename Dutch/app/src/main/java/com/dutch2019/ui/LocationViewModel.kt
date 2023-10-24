package com.dutch2019.ui

import androidx.lifecycle.LiveData
import com.dutch2019.base.BaseViewModel
import com.dutch2019.model.LocationData
import com.dutch2019.model.MutableListLiveData

class LocationViewModel : BaseViewModel() {

    private val _locationList = MutableListLiveData<LocationData>()
    val locationList: LiveData<List<LocationData>> get() = _locationList

    init {
        createEmptyLeastLocationItems()
    }

    fun addLocation(locationData: LocationData) {
        _locationList.add(locationData)
    }

    fun removeAtLocationList(position: Int) {
        _locationList.remove(_locationList.value!![position])
    }


    fun getLocationList(): List<LocationData> {
        return _locationList.value!!
    }

    fun changeLocationListItem(position: Int, locationData: LocationData) {
        _locationList.set(index = position, element = locationData)
    }

    private fun createEmptyLeastLocationItems() {
        repeat(3) {
            _locationList.add(LocationData())
        }
    }
}


// 설정한 위치 리스트
// 중간지점
// 비율변경지점