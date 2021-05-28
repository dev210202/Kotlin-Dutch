package com.dutch2019.ui.middle

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dutch2019.base.BaseViewModel
import com.dutch2019.model.LocationInfo
import com.dutch2019.model.RouteTimeData
import com.dutch2019.model.StartEndPointData
import com.dutch2019.network.Service
import com.google.gson.GsonBuilder
import com.skt.Tmap.TMapData
import com.skt.Tmap.TMapPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class MiddleLocationViewModel : BaseViewModel() {

    private var locationlist = ArrayList<LocationInfo>()

    private var centerPoint = TMapPoint(0.0, 0.0)

    private val _middleLocationAddress = MutableLiveData<String>()
    val middleLocationAddress: LiveData<String> get() = _middleLocationAddress

    private val _nearStationName = MutableLiveData<String>()
    val nearStationName: LiveData<String> get() = _nearStationName

    private val _totalRouteTime = MutableLiveData<String>()
    val totalRouteTime: LiveData<String> get() = _totalRouteTime

    fun getMiddleRouteTime(centerPoint: TMapPoint, latitude : Double, longitude : Double) {
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
        var startEndPointData = StartEndPointData(
            centerPoint.longitude,
            centerPoint.latitude,
            longitude,
            latitude
        )
        service.getRouteTime(startEndPointData).enqueue(object : retrofit2.Callback<RouteTimeData> {
            override fun onFailure(call: Call<RouteTimeData>, t: Throwable) {
                Log.e("data error", t.toString())
            }

            override fun onResponse(call: Call<RouteTimeData>, response: Response<RouteTimeData>) {
                val value = response.body()
                if (value != null) {
                    var totalSecond = value.features[0].properties.totalTime
                    _totalRouteTime.postValue(convertTime(totalSecond))
                    Log.i("MIDDLEROUTE convertTime", convertTime(totalSecond))
                }
            }

        })
    }

    fun setLocationList(list: ArrayList<LocationInfo>) {
        locationlist = list
    }

    fun getLocationList(): ArrayList<LocationInfo> {
        return locationlist
    }

    fun calculateCenterPoint(locationList: ArrayList<LocationInfo>): TMapPoint {
        var totalLatitude = 0.0
        var totalLongitude = 0.0
        for (i in 0 until locationList.size) {
            totalLatitude += locationList[i].latitude
            totalLongitude += locationList[i].longitude
        }

        return TMapPoint(
            totalLatitude / locationList.size,
            totalLongitude / locationList.size
        )
    }

    fun setCenterPoint(point: TMapPoint) {
        centerPoint = point
    }

    fun getCenterPoint(): TMapPoint = centerPoint

    fun setLocationAddress(point: TMapPoint) {
        val tMapData = TMapData()
        var locationAddress: String


        CoroutineScope(Dispatchers.IO).launch {
            locationAddress = try {
                tMapData.convertGpsToAddress(point.latitude, point.longitude)
            } catch (e: Exception) {
                "상세주소가 없습니다."
            }
            async(Dispatchers.Main) {
                _middleLocationAddress.value = locationAddress
            }
        }
    }

    fun setNearSubway(point: TMapPoint) {

        val stationData = TMapData()
        var subwayName: String

        CoroutineScope(Dispatchers.IO).launch {
            val tMapPOIItems = stationData.findAroundNamePOI(
                point,
                "지하철",
                20,
                3
            )
            subwayName = if (tMapPOIItems.isEmpty()) {
                "근처 지하철이 없습니다."
            } else {
                tMapPOIItems[0].poiName
            }

            async(Dispatchers.Main) {
                _nearStationName.value = subwayName
            }
        }
    }

    /*
        fun resetChangePoint(tMapView: TMapView) {
            tMapView.removeMarkerItem2("ratiomarkerItem")
        }
    */
    private fun convertTime(time: String): String {
        var result = ""
        var totalTime = time.toInt()
        if (totalTime >= 3600) {
            var hour = totalTime / 3600
            totalTime %= 3600
            result = hour.toString() + "시간"
        }
        if (totalTime >= 60) {
            var minute = totalTime / 60
            totalTime %= 60
            result += minute.toString() + "분"
        }
        if(totalTime > 0){
            result += totalTime.toString() + "초"
        }
        return result
    }

    fun resetRouteTime(){
        _totalRouteTime.postValue(" ")
    }
}
