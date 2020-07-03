package org.first.dutchv11

import android.location.Location
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.skt.Tmap.TMapData
import org.first.dutchv11.Data.LocationSetData

class MiddleLocationViewModel : ViewModel() {
    var totalLatitude = 0.0
    var totalLongitude = 0.0
    var middleLocationAddress = MutableLiveData<String>()
    fun calculateMiddleLocation(){
        for(i in 0..LocationSetData.data.size - 1){
            totalLatitude += LocationSetData.data[i].latitude
            totalLongitude += LocationSetData.data[i].longitude
        }

        var tMapData = TMapData()
        try{
            middleLocationAddress.value = tMapData.convertGpsToAddress(totalLatitude/LocationSetData.data.size, totalLongitude/LocationSetData.data.size)
            Log.e("Middle", middleLocationAddress.value)
        }catch (e : Exception){
            e.printStackTrace()
        }

    }

}