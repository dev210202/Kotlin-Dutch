package org.first.dutchv11

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.skt.Tmap.TMapData
import com.skt.Tmap.TMapPOIItem
import org.first.dutchv11.Data.LocationData
import java.util.ArrayList

class SearchLocationViewModel : ViewModel() {
    lateinit var input: String
    lateinit var item: TMapPOIItem
    var locationList = MutableLiveData<ArrayList<LocationData>>()
    lateinit var locationArrayList : ArrayList<LocationData>
    var isDataLoadFail = MutableLiveData<Boolean>()

    lateinit var locationdata : LocationData
    fun searchLocationData() {
        var tMapData = TMapData()
//        locationList = MutableLiveData<ArrayList<LocationData>>()
        locationArrayList = ArrayList<LocationData>()
        if (input != "") {

            Log.e("Search Start", "1")
            tMapData.findAllPOI(input, object : TMapData.FindAllPOIListenerCallback {
                override fun onFindAllPOI(p0: ArrayList<TMapPOIItem>?) {
                    if (p0?.size == 0) {
                        isDataLoadFail.postValue(true)
                        Log.e("FAIL","!!")
                    } else {
                        for (i in 0..p0!!.size - 1)
                        {
                            item = p0.get(i)

                            if(isItemDataOK(item)){
                                var address = item.upperAddrName + " " + item.middleAddrName+ " " + item.lowerAddrName
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

            })
        }


    }

    fun isItemDataOK(item : TMapPOIItem) : Boolean{
        if(item.poiName != null && item.upperAddrName != null && item.middleAddrName != null && item.lowerAddrName != null && item.poiPoint != null){
            return true
        }
        else{
            return false
        }
    }
}






