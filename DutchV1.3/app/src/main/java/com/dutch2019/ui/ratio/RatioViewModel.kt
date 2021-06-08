package com.dutch2019.ui.ratio

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dutch2019.base.BaseViewModel
import com.dutch2019.model.LocationInfoList
import com.skt.Tmap.TMapPoint

class RatioViewModel : BaseViewModel() {

    var locationInfoList = LocationInfoList()
    var pointList= ArrayList<TMapPoint>()

    private val _ratio = MutableLiveData<String>()
    val ratio : LiveData<String> get() = _ratio

}