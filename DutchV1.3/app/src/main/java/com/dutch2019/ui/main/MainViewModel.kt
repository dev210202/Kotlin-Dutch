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

    private var locationRepository = DBRepository()

    private val _locationList = MutableLiveData<ArrayList<LocationInfo>>(ArrayList<LocationInfo>())
    val locationList: LiveData<ArrayList<LocationInfo>> get() = _locationList

    var checkLocationInfo = LocationInfo()

    fun addLocation(locationInfo: LocationInfo){
        _locationList.value!!.add(locationInfo)
    }
    fun setLocationList(list : ArrayList<LocationInfo>){
        _locationList.value = list
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