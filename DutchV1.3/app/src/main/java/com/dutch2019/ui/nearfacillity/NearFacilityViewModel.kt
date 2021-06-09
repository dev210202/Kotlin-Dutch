package com.dutch2019.ui.nearfacillity

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dutch2019.base.BaseViewModel
import com.dutch2019.model.LocationInfo
import com.dutch2019.network.Service
import com.dutch2019.repository.APIRepository
import com.google.gson.GsonBuilder
import com.skt.Tmap.TMapData
import com.skt.Tmap.TMapPOIItem
import com.skt.Tmap.TMapPoint
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class NearFacilityViewModel : BaseViewModel() {

    var locationPoint = TMapPoint(0.0, 0.0)
    private lateinit var locationArrayList: ArrayList<LocationInfo>

    private val _locationList = MutableLiveData<ArrayList<LocationInfo>>()
    val locationList: LiveData<ArrayList<LocationInfo>> get() = _locationList


    private val apiRepository = APIRepository.getInstance()
    fun initList() {
        _locationList.value = ArrayList()
    }

    fun setLocaitonPoint(lat: Double, lon: Double) {
        locationPoint.latitude = lat
        locationPoint.longitude = lon
    }

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

    fun searchNearFacility(centerPoint: TMapPoint, category: String) {
        val tMapData = TMapData()
        tMapData.findAroundNamePOI(
            centerPoint,
            category,
            3,
            50
        ) { p0 ->
            locationArrayList = ArrayList()
            if (p0 != null) {
                for (i in 0 until p0.size) {
                    val item = p0[i]
                    if (isItemDataOK(item)) {

                        locationArrayList.add(itemFilter(item))
                    }

                }
                _locationList.postValue(locationArrayList)
            }
        }
    }


    private fun isItemDataOK(item: TMapPOIItem): Boolean {
        return item.poiName != null && item.upperAddrName != null && item.poiPoint != null
    }

    private fun itemFilter(item: TMapPOIItem): LocationInfo {

        var address = ""
        val id = Integer.valueOf(item.poiid)
        if (item.upperAddrName != null) {
            address += item.upperAddrName
        }
        if (item.middleAddrName != null) {
            address += " " + item.middleAddrName
        }
        if (item.lowerAddrName != null) {
            address += " " + item.lowerAddrName
        }
        return LocationInfo(
            id,
            item.poiName,
            address,
            item.poiPoint.latitude,
            item.poiPoint.longitude
        )
    }
}