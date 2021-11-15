package com.dutch2019.ui.near

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dutch2019.base.BaseViewModel
import com.skt.Tmap.TMapData
import com.skt.Tmap.TMapPOIItem
import com.skt.Tmap.TMapPoint
import com.dutch2019.model.LocationData
import com.dutch2019.util.filtNull
import com.dutch2019.util.filtZero

class NearViewModel : BaseViewModel() {

    var searchPoint = TMapPoint(0.0, 0.0)
    private val _facilityList = MutableLiveData<ArrayList<LocationData>>()
    val facilityList: LiveData<ArrayList<LocationData>> get() = _facilityList
    fun setNearFacilityCategory(input: String): String {

        when (input) {
            "대중교통" -> {
                return "지하철;버스;버스정류장;"
            }
            "문화시설" -> {
                return "주요시설물;문화시설;영화관;놀거리;"
            }
            "음식점" -> {
                return "식음료;한식;중식;양식;"
            }
            "카페" -> {
                return "카페"
            }
        }
        return ""
    }

    fun searchNearFacility(point: TMapPoint, category: String) {
        Log.i("category", category)
        val tMapData = TMapData()
        tMapData.findAroundNamePOI(
            point,
            category,
            3,
            50
        ) { p0 ->
            val locationArrayList = ArrayList<LocationData>()
            if (p0 != null) {
                for (i in 0 until p0.size) {
                    val item = p0[i]


                    Log.i("item name", ""+item.poiName)
                    Log.i("item address", ""+item.poiAddress)
                    Log.i("item tel", ""+item.telNo)
                    Log.i("item roadName", ""+item.roadName)
                    Log.i("item buildingNo1", ""+item.buildingNo1)
                    Log.i("item buildingNo2", ""+item.buildingNo2)



                    if (isItemDataOK(item)) {
                        locationArrayList.add(
                            LocationData(
                                item.poiid,
                                item.poiName,
                                filtNull(item.poiAddress)+ filtNull(" " + item.buildingNo1) + " "+ filtNull(filtZero(" "+item.buildingNo2)),
                                filtNull(" "+ item.telNo),
                                item.poiPoint.latitude,
                                item.poiPoint.longitude
                            )
                        )
                    }

                }
                _facilityList.postValue(locationArrayList)
            }
        }
    }

    private fun isItemDataOK(item: TMapPOIItem): Boolean {
        return item.poiName != null && item.upperAddrName != null && item.poiPoint != null
    }


}