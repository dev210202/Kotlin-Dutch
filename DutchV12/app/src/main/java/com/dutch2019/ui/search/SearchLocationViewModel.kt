package com.dutch2019.ui.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dutch2019.model.LocationData
import com.skt.Tmap.TMapData
import com.skt.Tmap.TMapPOIItem
import java.util.*

class SearchLocationViewModel : ViewModel() {

    var locationList = MutableLiveData<ArrayList<LocationData>>()
    var isDataLoadFail = MutableLiveData<Boolean>()

    fun searchLocationData(input: String) {

        var item: TMapPOIItem
        val tMapData = TMapData()
        if (input.isNotEmpty()) {
            tMapData.findAllPOI(input) { arrayList ->
                val locationArrayList = ArrayList<LocationData>()
                if (arrayList.isEmpty()) {
                    isDataLoadFail.value = true
                } else {
                    arrayList.forEach { poiItem ->
                        var address = ""
                        item = poiItem
                        if (isItemDataExist(item)) {
                            address = setAddressName(address, item)
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
                locationList.postValue(locationArrayList)
            }
        }
    }

    private fun isItemDataExist(item: TMapPOIItem): Boolean {
        return item.poiName != null && item.upperAddrName != null && item.poiPoint != null
    }

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