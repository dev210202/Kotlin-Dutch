package com.dutch2019.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dutch2019.base.BaseViewModel
import com.dutch2019.model.LocationData
import com.dutch2019.model.MutableListLiveData
import com.dutch2019.repository.DBRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val dataBaseRepository: DBRepository
) : BaseViewModel() {

    private val _isCompleteLoadDatabase = MutableLiveData<Boolean>(false)
    val isCompleteLoadDatabase: LiveData<Boolean> get() = _isCompleteLoadDatabase

    private val _locationList = MutableListLiveData<LocationData>()
    val locationList: LiveData<List<LocationData>> get() = _locationList

    private var searchLocationList = listOf<LocationData>()
    private var locationDBList = listOf<LocationData>()
    private var _selectedItemIndex = -1

    init {
        createEmptyLeastLocationItems()
    }

    fun setCompleteLoadDatabase() {
        _isCompleteLoadDatabase.postValue(true)
    }

    fun isCompleteLoadDatabase(): Boolean {
        return _isCompleteLoadDatabase.value!!
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

    fun getLocationDBList(): List<LocationData> {
        return locationDBList
    }

    fun getSelectedItemIndex(): Int {
        return _selectedItemIndex
    }

    fun setSelectedItemIndex(index: Int) {
        _selectedItemIndex = index
    }

    fun changeLocationListItem(position: Int, locationData: LocationData) {
        _locationList.set(index = position, element = locationData)
    }

    private fun createEmptyLeastLocationItems() {
        repeat(3) {
            _locationList.add(LocationData())
        }
    }

    fun saveSearchDataIntoDB(data: LocationData) = viewModelScope.launch(Dispatchers.IO) {
        runCatching {
            dataBaseRepository.insertSearchData(data)
        }.onSuccess {
            setCompleteLoadDatabase()
        }.onFailure { throwable ->
            throw throwable
        }
    }


    fun loadSearchData() = viewModelScope.launch(Dispatchers.IO) {
        runCatching {
            dataBaseRepository.getSearchData()
        }.onSuccess { list ->
            locationDBList = list
        }.onFailure { throwable ->
            throw throwable
        }
    }

    fun addDBData(data: LocationData) {
        locationDBList.toMutableList().apply {
            add(0, data)
        }.apply {
            locationDBList = this
        }
    }

    fun changeSearchLocationList(list: List<LocationData>) {
        locationDBList.toMutableList().apply {
            list.forEach { locationData ->
                this.remove(locationData)
            }
        }.apply {
            locationDBList = this
        }
    }

    fun deleteCheckedList(list: List<LocationData>) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                dataBaseRepository.deleteRecentData(list.toMutableList())
            }.onFailure { throwable ->
                throw throwable
            }
        }
    }
}