package com.dutch2019.ui.search

import android.location.Location
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dutch2019.base.BaseViewModel
import com.dutch2019.model.LocationData
import com.skt.Tmap.TMapData
import com.skt.Tmap.TMapPOIItem
import com.skt.Tmap.TMapTapi
import java.util.*
import kotlin.collections.ArrayList

class SearchLocationViewModel : BaseViewModel() {

    private val _locationList = MutableLiveData<ArrayList<LocationData>>()
    val locationList: LiveData<ArrayList<LocationData>> get() = _locationList
    var isDataLoadFail = MutableLiveData<Boolean>()

    init {
        _locationList.value = ArrayList()
    }

    fun searchLocationData(input: String) {
        val tMapData = TMapData()
        if (input.isNotEmpty()) {
            tMapData.findAllPOI(input) { arrayList ->
                setLocationData(arrayList).let { list ->
                    _locationList.postValue(list)
                }
            }
        }
    }

    private fun setLocationData(arrayList: ArrayList<TMapPOIItem>): ArrayList<LocationData> {

        val locationArrayList = ArrayList<LocationData>()
        var item: TMapPOIItem
        if (arrayList.isEmpty()) {
            isDataLoadFail.postValue(true)
        } else {
            arrayList.forEach { poiItem ->
                var address = ""
                item = poiItem
                if (isItemDataExist(item)) {
                    address = setAddressName(address, item)
                    Log.i("ADRESS", address)
                    locationArrayList.add(
                        LocationData(
                            item.poiName,
                            address,
                            item.poiPoint.latitude,
                            item.poiPoint.longitude
                        )
                    )
                }
            }
        }
        return locationArrayList
    }

    private fun isItemDataExist(item: TMapPOIItem): Boolean = (item.poiName != null && item.upperAddrName != null && item.poiPoint != null)

    private fun setAddressName(input: String, item: TMapPOIItem): String {
        var address = input
        if (item.upperAddrName.isNotEmpty()) {
            address = address.plus(item.upperAddrName)
            address += item.upperAddrName
        }
        if (item.middleAddrName.isNotEmpty()) {
            address = address.plus(" " + item.middleAddrName)
        }
        if (item.lowerAddrName.isNotEmpty()) {
            address = address.plus(" " + item.lowerAddrName)
        }
        return address
    }
}