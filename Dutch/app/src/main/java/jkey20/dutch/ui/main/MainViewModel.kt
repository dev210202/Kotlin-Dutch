package jkey20.dutch.ui.main

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dutch2019.base.BaseViewModel
import com.skt.Tmap.TMapPOIItem
import jkey20.dutch.model.LocationData

class MainViewModel : BaseViewModel() {
    private val _checkLocationList = MutableLiveData<ArrayList<LocationData>>(ArrayList<LocationData>())
    val checkLocationList : LiveData<ArrayList<LocationData>> get() = _checkLocationList

    fun addLocation(locationData: LocationData){
        _checkLocationList.value!!.add(locationData)
    }
}