package org.first.dutchv11

import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.databinding.BaseObservable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.skt.Tmap.TMapData
import com.skt.Tmap.TMapPOIItem
import org.first.dutchv11.Data.LocationData
import java.util.ArrayList
import java.util.logging.Handler

class SearchLocationViewModel : ViewModel() {
    lateinit var input: String
    lateinit var item: TMapPOIItem
    var locationList = mutableListOf<LocationData>()
    var isComplete = 0
    var COMPLETE = 1
    var LOAD_FAIL = -1
    lateinit var locationdata : LocationData
    fun searchLocationData() {
        var tMapData = TMapData()
        if (input != "") {

            Log.e("Search Start", "1")
            tMapData.findAllPOI(input, object : TMapData.FindAllPOIListenerCallback {
                override fun onFindAllPOI(p0: ArrayList<TMapPOIItem>?) {
                    if (p0?.size == 0) {
                        isComplete = LOAD_FAIL
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
                                locationList.add(locationdata)
                            }

                            isComplete = COMPLETE

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






