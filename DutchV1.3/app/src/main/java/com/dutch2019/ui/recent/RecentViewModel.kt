package com.dutch2019.ui.recent

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dutch2019.base.BaseViewModel
import com.dutch2019.model.LocationDataDB
import com.dutch2019.repository.LocationRepository
import kotlinx.coroutines.launch

class RecentViewModel : BaseViewModel() {

    private var locationRepository = LocationRepository.getInstance()

    private val _locationList = MutableLiveData<List<LocationDataDB>>()
    val locationList: LiveData<List<LocationDataDB>> get() = _locationList
    fun initList() {
        _locationList.value = listOf()
    }

    fun initDB(application: Application) {
        viewModelScope.launch {
            locationRepository.setRecentDB(application)
        }
    }

    fun getRecentLocationDB() {
        viewModelScope.launch {
            _locationList.value = locationRepository.getLocationListData()
        }
    }

    fun deleteLocationDB(list: List<LocationDataDB>) {
        viewModelScope.launch {
            locationRepository.deleteLocationList(list)
            getRecentLocationDB()
        }
    }

    fun deleteAllLocationDB() {
        viewModelScope.launch {
            locationRepository.deleteAll()
        }
    }
}