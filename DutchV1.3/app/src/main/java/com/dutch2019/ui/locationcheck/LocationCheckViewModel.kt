package com.dutch2019.ui.locationcheck

import android.graphics.BitmapFactory
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dutch2019.R
import com.dutch2019.base.BaseViewModel
import com.dutch2019.model.LocationData
import com.skt.Tmap.TMapMarkerItem
import com.skt.Tmap.TMapPoint

class LocationCheckViewModel : BaseViewModel() {

    private val _locationData = MutableLiveData<LocationData>()
    val locationData : LiveData<LocationData> get() = _locationData

    init {
        _locationData.value = LocationData("","", 0.0, 0.0)
    }
    fun setData(data: LocationData) {
        _locationData.value = data
    }
}