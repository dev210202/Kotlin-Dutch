package com.dutch2019.ui.search

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.skt.Tmap.TMapData
import com.skt.Tmap.TMapPOIItem
import com.dutch2019.Data.LocationData
import java.util.ArrayList

class SearchLocationViewModel : ViewModel() {
    lateinit var input: String
    private lateinit var item: TMapPOIItem
    var locationList = MutableLiveData<ArrayList<LocationData>>()
    var locationArrayList = ArrayList<LocationData>()
    var isDataLoadFail = MutableLiveData<Boolean>()

    private lateinit var locationdata: LocationData
    fun searchLocationData() {
        val tMapData = TMapData()
//        locationList = MutableLiveData<ArrayList<LocationData>>()

        if (input != "") {

            Log.e("Search Start", "1")
            tMapData.findAllPOI(input) { p0 ->
                if (p0?.size == 0) {
                    isDataLoadFail.postValue(true)
                    Log.e("FAIL", "!!")
                } else {
                    for (i in 0 until p0!!.size) {
                        item = p0[i]

                        if (isItemDataOK(item)) {
                            val address =
                                item.upperAddrName + " " + item.middleAddrName + " " + item.lowerAddrName
                            locationdata = LocationData(
                                item.poiName,
                                address,
                                item.poiPoint.latitude,
                                item.poiPoint.longitude
                            )
                            locationArrayList.add(locationdata)
                            locationList.postValue(locationArrayList)
                        }


                    }
                    Log.e("Search End", "1")
                }
            }
        }


    }

    fun isItemDataOK(item: TMapPOIItem): Boolean {
        return item.poiName != null && item.upperAddrName != null && item.middleAddrName != null && item.lowerAddrName != null && item.poiPoint != null
    }
}






