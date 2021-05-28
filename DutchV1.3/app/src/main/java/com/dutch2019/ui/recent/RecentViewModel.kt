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
            var list = locationRepository.getLocationListData()
            var reverseList = reverseList(list)
            _locationList.value = reverseList
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
            getRecentLocationDB()
        }
    }

    // 최근 검색한 위치가 최상단으로 올라오게하려면 리스트를 반대로 뒤집어야함
    private fun reverseList(list: List<LocationDataDB>): List<LocationDataDB> {
        var newList = arrayListOf<LocationDataDB>()

        for (i in list.size -1 downTo 0) {
            var listValue = list[i]
            newList.add(list[i])
        }
        return newList
    }
}