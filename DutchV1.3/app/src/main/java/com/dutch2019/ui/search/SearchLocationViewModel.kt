package com.dutch2019.ui.search

import android.location.Location
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
//                _locationList.value = locationArrayList


            }
        }
    }

    private fun setLocationData(arrayList: ArrayList<TMapPOIItem>): ArrayList<LocationData> {

        val locationArrayList = ArrayList<LocationData>()
        var item: TMapPOIItem
        if (arrayList.isEmpty()) {
            // 검색된 결과 없음
            isDataLoadFail.postValue(true)
        } else {
            // 검색결과 존재
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
        return locationArrayList
    }

    private fun isItemDataExist(item: TMapPOIItem): Boolean {
        return item.poiName != null && item.upperAddrName != null && item.poiPoint != null
    }

    private fun setAddressName(input: String, item: TMapPOIItem): String {
        var address = input
        if (item.upperAddrName.isNotEmpty() && item.upperAddrName != null) {
            address += item.upperAddrName
        }
        if(!item.middleAddrName.isNullOrEmpty()){
            address += " " + item.middleAddrName
        }
        if (!item.lowerAddrName.isNullOrEmpty()) {
            address += " " + item.lowerAddrName
        }
        return address
    }
}