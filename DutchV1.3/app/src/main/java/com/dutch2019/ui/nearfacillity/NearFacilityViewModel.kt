package com.dutch2019.ui.nearfacillity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dutch2019.base.BaseViewModel
import com.dutch2019.model.LocationInfo
import com.skt.Tmap.TMapData
import com.skt.Tmap.TMapPOIItem
import com.skt.Tmap.TMapPoint

class NearFacilityViewModel : BaseViewModel() {

    var locationPoint = TMapPoint(0.0, 0.0)
    private lateinit var locationArrayList: ArrayList<LocationInfo>

    private val _locationList = MutableLiveData<ArrayList<LocationInfo>>()
    val locationList: LiveData<ArrayList<LocationInfo>> get() = _locationList
//    var errorMessage = MutableLiveData<String>()
//    var detailInfo = MutableLiveData<String>()

    fun initList() {
        _locationList.value = ArrayList()
    }

    fun setLocaitonPoint(lat: Double, lon: Double) {
        locationPoint.latitude = lat
        locationPoint.longitude = lon
    }
/*
    fun getDetailInfo(poiId: Int) {

        val gson = GsonBuilder().setLenient().create()

        val loggingInterceptor = HttpLoggingInterceptor()
        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .build()


        val retrofit = Retrofit.Builder()
            .baseUrl("https://apis.openapi.sk.com/tmap/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
        val service = retrofit.create(Service::class.java)
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        service.getDetailInfo(poiId.toString()).enqueue(object : Callback<DetailData> {
            override fun onFailure(call: Call<DetailData>, t: Throwable) {
                Log.e("data error", t.toString())
            }

            override fun onResponse(call: Call<DetailData>, response: Response<DetailData>) {

                val value = response.body()
                if (value != null) {
                    detailInfo.value = value.poiDetailInfo.additionalInfo
                }
            }

        })
    }
*/
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