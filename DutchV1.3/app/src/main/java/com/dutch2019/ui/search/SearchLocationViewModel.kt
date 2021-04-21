package com.dutch2019.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dutch2019.base.BaseViewModel
import com.dutch2019.model.LocationInfo
import com.skt.Tmap.TMapData
import com.skt.Tmap.TMapPOIItem
import kotlin.collections.ArrayList

class SearchLocationViewModel : BaseViewModel() {

    private val _locationList = MutableLiveData<ArrayList<LocationInfo>>()
    val locationList: LiveData<ArrayList<LocationInfo>> get() = _locationList
    var isDataLoadFail = MutableLiveData<Boolean>()
    var locationPosition : Int = 0

    fun init(){
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

    private fun setLocationData(arrayList: ArrayList<TMapPOIItem>): ArrayList<LocationInfo> {

        val locationArrayList = ArrayList<LocationInfo>()
        var item: TMapPOIItem
        if (arrayList.isEmpty()) {
            toastValue.postValue("검색된 위치가 없습니다.")
            isDataLoadFail.postValue(true)
        } else {
            for(i in 0 until arrayList.size) {
                val poiItem = arrayList[i]
                var address = ""
                item = poiItem
                if (isItemDataExist(item)) {
                    address = setAddressName(address, item)
                    locationArrayList.add(
                        LocationInfo(
                            locationPosition,
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