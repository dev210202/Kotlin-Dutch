package com.dutch2019.ui.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dutch2019.base.BaseViewModel
import com.dutch2019.model.LocationDBData
import com.dutch2019.model.LocationData
import com.dutch2019.model.MutableListLiveData
import com.dutch2019.repository.DBRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val dataBaseRepository: DBRepository) :
    BaseViewModel() {

    private val _isConfirmedSktMapApikey = MutableLiveData<Boolean>(false)
    val isConfirmedSktMapApikey: LiveData<Boolean> get() = _isConfirmedSktMapApikey

    private val _locationList = MutableListLiveData<LocationData>()
    val locationList: LiveData<List<LocationData>> get() = _locationList

    private val _recentLocationList = MutableListLiveData<LocationDBData>()
    val recentLocationList: LiveData<List<LocationDBData>> get() = _recentLocationList

    private val _selectedItemIndex = MutableLiveData<Int>()
    val selectedItemIndex: LiveData<Int> get() = _selectedItemIndex

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

    fun removeAtLocationList(position: Int) {
        _locationList.remove(_locationList.value!![position])
    }

    fun setLocationList(list: List<LocationData>) {
        _locationList.value = list
    }

    fun getLocationList(): List<LocationData> {
        return _locationList.value!!
    }

    fun loadRecentDB() {
        viewModelScope.launch {
            _recentLocationList.value = dataBaseRepository.getRecentData()
        }
    }

    fun getRecentLocationList(): List<LocationDBData> {
        return _recentLocationList.value!!
    }

    fun getSelectedItemIndex(): Int {
        return _selectedItemIndex.value!!
    }

    fun setSelectedItemIndex(index: Int) {
        _selectedItemIndex.value = index
    }

    fun changeLocationListItem(position: Int, locationData: LocationData) {
        _locationList.set(index = position, element = locationData)
    }

    private fun createEmptyLeastLocationItems() {
        _locationList.add(LocationData())
        _locationList.add(LocationData())
        _locationList.add(LocationData())
    }
}