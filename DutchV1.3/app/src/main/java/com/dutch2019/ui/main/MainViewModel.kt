package com.dutch2019.ui.main

import android.content.Intent
import android.graphics.Color
import android.location.Location
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dutch2019.base.BaseViewModel
import com.dutch2019.db.AppDB
import com.dutch2019.model.LocationInfo

class MainViewModel : BaseViewModel() {
    private val list = ArrayList<LocationInfo>()

    private val _dynamicButtonData = MutableLiveData<ArrayList<LocationInfo>>()
    val dynamicButtonData: LiveData<ArrayList<LocationInfo>> get() = _dynamicButtonData

    init {
        _dynamicButtonData.value = list
    }


    fun addDynamicButtonData() {
        //list.add(setDummyLocationData())
        _dynamicButtonData.value = _dynamicButtonData.value?.apply{
            add(setDummyLocationData())
        }
    }


    private fun setDummyLocationData(): LocationInfo {
        return LocationInfo(
            list.size,
            "위치를 입력해주세요" + list.size,
            "",
            0.0,
            0.0
        )
    }

}