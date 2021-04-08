package com.dutch2019.ui.nearfacillity

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dutch2019.base.BaseViewModel
import com.dutch2019.model.DetailData
import com.dutch2019.model.LocationInfo
import com.dutch2019.network.Service
import com.google.gson.GsonBuilder
import com.kakao.kakaolink.v2.KakaoLinkResponse
import com.kakao.kakaolink.v2.KakaoLinkService
import com.kakao.message.template.ButtonObject
import com.kakao.message.template.ContentObject
import com.kakao.message.template.LinkObject
import com.kakao.message.template.LocationTemplate
import com.kakao.network.ErrorResult
import com.kakao.network.callback.ResponseCallback
import com.skt.Tmap.TMapData
import com.skt.Tmap.TMapPOIItem
import com.skt.Tmap.TMapPoint
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList

class NearFacilityViewModel : BaseViewModel() {

    var locationPoint = TMapPoint(0.0, 0.0)
    lateinit var locationArrayList: ArrayList<LocationInfo>

    val _locationList = MutableLiveData<ArrayList<LocationInfo>>()
    val locationList : LiveData<ArrayList<LocationInfo>> get() = _locationList
    var errorMessage = MutableLiveData<String>()
    var detailInfo = MutableLiveData<String>()

    fun init(){
        _locationList.value = ArrayList()
    }

    fun setLocaitonPoint(lat: Double, lon: Double) {
        locationPoint.latitude = lat
        locationPoint.longitude = lon
    }

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

                var value = response.body()
                Log.e("data value ", value.toString())
                // valu.~ 으로 값 가져오기
                if (value != null) {
                    Log.e("!additionalInfo", value.poiDetailInfo.additionalInfo.toString())
                    Log.e("!desc", value.poiDetailInfo.desc.toString())
                    detailInfo.value = value.poiDetailInfo.additionalInfo.toString()
                }
            }

        })
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
    fun searchNearFacility(centerPoint: TMapPoint, category : String){
        val tMapData = TMapData()
        tMapData.findAroundNamePOI(
            centerPoint,
            category,
            3,
            50
        ) { p0 ->
            locationArrayList = ArrayList<LocationInfo>()
            Log.e("!!!!", " " + p0.size)
            if (p0 != null) {
                for (i in 0 until p0.size) {
                    val item = p0[i]
                    if (isItemDataOK(item)) {

                        locationArrayList.add(itemFilter(item, i))
                    }

                }
                _locationList.postValue(locationArrayList)
            }
        }
    }




    fun isItemDataOK(item: TMapPOIItem): Boolean {
        return item.poiName != null && item.upperAddrName != null && item.poiPoint != null
    }

    fun itemFilter(item: TMapPOIItem, i: Int): LocationInfo {

        var address = ""
        var id = Integer.valueOf(item.poiid)
        Log.i("!@!id!@!@", ""+id)
        if (item.upperAddrName != null) {
            address += item.upperAddrName
            Log.e("upperAddrNameN", item.upperAddrName)
        }
        if (item.middleAddrName != null) {
            address += " " + item.middleAddrName
            Log.e("middleAddrNameN", item.middleAddrName)
        }
        if (item.lowerAddrName != null) {
            address += " " + item.lowerAddrName
            Log.e("lowerAddrNameN", item.lowerAddrName)
        }
        val locationData = LocationInfo(
            id,
            item.poiName,
            address,
            item.poiPoint.latitude,
            item.poiPoint.longitude
        )
        Log.e("address!!!", address)
        locationArrayList.add(locationData)
        return locationData
    }


}