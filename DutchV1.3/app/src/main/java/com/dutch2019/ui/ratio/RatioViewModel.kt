package com.dutch2019.ui.ratio

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dutch2019.base.BaseViewModel
import com.dutch2019.model.LocationInfoList

class RatioViewModel : BaseViewModel() {

    var locationInfoList = LocationInfoList()

    private val _ratio = MutableLiveData<String>()
    val ratio : LiveData<String> get() = _ratio

    fun setRatio(value : String){
        _ratio.postValue(value)
    }

}