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


    private var searchLocationList = listOf<LocationData>()
    private var locationDBList = listOf<LocationData>()




    fun getLocationDBList(): List<LocationData> {
        return locationDBList
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