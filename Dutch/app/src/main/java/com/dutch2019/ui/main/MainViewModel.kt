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
import com.dutch2019.util.sortByRecentList
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
        val list = _locationList.value!!.toMutableList()
        list.add(locationData)
        _locationList.value = list
    }

    fun removeAtLocationList(position: Int) {
        /*
            list를 새롭게 변경하는 이유

            locationList에서 삭제하면 list의 아이템의 참조가 같은거로 판단해서 recyclerview의 item이 업데이트 되지 않는다.
            따라서 리스트를 복사한 새로운 리스트에서 제거한 뒤 다시 리스트의 값을 변경하여 반영하게 한다.
         */
        val list = _locationList.value!!.toMutableList()
        list.removeAt(position)
        _locationList.value = list
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
        val list = _locationList.value!!.toMutableList()
        list.set(index = position, element = locationData)
        _locationList.value = list
    }
}