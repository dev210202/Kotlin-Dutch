package com.dutch2019.ui.nearfacillity

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dutch2019.model.DetailData
import com.dutch2019.model.LocationData
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

class NearFacilityViewModel : ViewModel() {
    var locationList = MutableLiveData<ArrayList<LocationData>>()
    lateinit var locationArrayList: ArrayList<LocationData>
    var errorMessage = MutableLiveData<String>()
    var detailInfo = MutableLiveData<String>()

    fun getDetailInfo(poiId: String) {


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
        service.getDetailInfo(poiId).enqueue(object : Callback<DetailData> {
            override fun onFailure(call: Call<DetailData>, t: Throwable) {
                Log.e("data error", t.toString())
            }

            override fun onResponse(call: Call<DetailData>, response: Response<DetailData>) {

                var value = response.body()
                Log.e("data value ", value.toString())
                // valu.~ 으로 값 가져오기
                if (value != null) {
                    detailInfo.value = value.poiDetailInfo.additionalInfo.toString()
                }
            }

        })
    }

    fun searchNearTransport(centerPoint: TMapPoint) {
        val tMapData = TMapData()
        tMapData.findAroundNamePOI(
            centerPoint,
            "지하철;버스;버스정류장;",
            3,
            50
        ) { p0 ->
            locationArrayList = ArrayList<LocationData>()
            Log.e("!!!!", " " + p0.size)
            if (p0 != null) {
                for (i in 0 until p0.size) {
                    val item = p0[i]
                    if (isItemDataOK(item)) {

                        locationArrayList.add(itemFilter(item))
                    }

                }
                locationList.postValue(locationArrayList)
            }
        }
    }

    fun searchNearCulture(centerPoint: TMapPoint) {
        val tMapData = TMapData()
        tMapData.findAroundNamePOI(
            centerPoint,
            "주요시설물;문화시설;영화관;놀거리;",
            3,
            50
        ) { p0 ->
            locationArrayList = ArrayList<LocationData>()
            if (p0 != null) {
                for (i in 0 until p0.size) {
                    val item = p0[i]
                    if (isItemDataOK(item)) {
                        val address =
                            item.upperAddrName + " " + item.middleAddrName + " " + item.lowerAddrName
                        val locationData = LocationData(
                            item.poiName,
                            address,
                            item.poiPoint.latitude,
                            item.poiPoint.longitude
                        )

                        locationArrayList.add(locationData)
                        // SearchData.data.add(locationData)
                    }

                }
                locationList.postValue(locationArrayList)
            }
        }
    }

    fun searchNearFood(centerPoint: TMapPoint) {
        val tMapData = TMapData()
        tMapData.findAroundNamePOI(
            centerPoint,
            "식음료;한식;중식;양식;",
            3,
            50
        ) { p0 ->
            locationArrayList = ArrayList<LocationData>()
            if (p0 != null) {
                for (i in 0 until p0.size) {
                    val item = p0[i]
                    if (isItemDataOK(item)) {
                        val address =
                            item.upperAddrName + " " + item.middleAddrName + " " + item.lowerAddrName
                        val locationData = LocationData(
                            item.poiName,
                            address,
                            item.poiPoint.latitude,
                            item.poiPoint.longitude
                        )

                        locationArrayList.add(locationData)
                        // SearchData.data.add(locationData)
                    }

                }
                locationList.postValue(locationArrayList)
            }
        }
    }

    fun searchNearCafe(centerPoint: TMapPoint) {
        val tMapData = TMapData()
        tMapData.findAroundNamePOI(
            centerPoint,
            "카페",
            3,
            50
        ) { p0 ->
            locationArrayList = ArrayList<LocationData>()
            if (p0 != null) {
                for (i in 0 until p0.size) {
                    val item = p0[i]
                    if (isItemDataOK(item)) {
                        val address =
                            item.upperAddrName + " " + item.middleAddrName + " " + item.lowerAddrName
                        val locationData = LocationData(
                            item.poiName,
                            address,
                            item.poiPoint.latitude,
                            item.poiPoint.longitude
                        )

                        locationArrayList.add(locationData)
                        // SearchData.data.add(locationData)
                    }

                }
                locationList.postValue(locationArrayList)
            } else {

            }
        }
    }

    fun shareLocation(name: String, address: String, location: LocationData, context: Context) {
        val kakaoAddressResult = address.replace(" ", "%20")
        Log.e("!!!", kakaoAddressResult)
        var leftButtonObject = ButtonObject(
            "앱으로 돌아가기", // 왼쪽 버튼의 표시될 텍스트를 설정
            LinkObject.newBuilder()
                .setWebUrl("https://map.kakao.com/link/map/" + kakaoAddressResult + "," + name) //
                .setMobileWebUrl("https://map.kakao.com/link/map/" + kakaoAddressResult + "," + name)
                .build()
        )
        var params = LocationTemplate.newBuilder(
            kakaoAddressResult,
            ContentObject.newBuilder(
                name,
                "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2Ft3sEu%2FbtqJKvIDbb0%2FNOg8Ozhw0IgWkpagWecdr1%2Fimg.png",
                LinkObject.newBuilder()
                    .setWebUrl("https://map.kakao.com/link/map/$kakaoAddressResult," + location.latitude + "," + location.longitude)
                    .setMobileWebUrl("https://map.kakao.com/link/map/$kakaoAddressResult," + location.latitude + "," + location.longitude)
                    .build()
            )
                .setDescrption("https://map.kakao.com/link/map/$kakaoAddressResult," + location.latitude + "," + location.longitude)
                .build()
        ).setAddressTitle(name) // 위치 확인시 보여줄 제목칸에 선택한 주변시설의 명칭을 띄워줌
            .addButton(leftButtonObject) // 왼쪽버튼에 해당하는 ButtonObject를 추가
            .build()


        var serverCallbackArgs = mutableMapOf<String, String>()
        serverCallbackArgs.put("user_id", "current_user_id")
        serverCallbackArgs.put("product_id", "shared_product_id")


        KakaoLinkService.getInstance().sendDefault(context, params, serverCallbackArgs,
            object : ResponseCallback<KakaoLinkResponse?>() {
                override fun onFailure(errorResult: ErrorResult) {

                    errorMessage.postValue(errorResult.toString())

                }

                override fun onSuccess(result: KakaoLinkResponse?) {
                    // 템플릿 밸리데이션과 쿼터 체크가 성공적으로 끝남. 톡에서 정상적으로 보내졌는지 보장은 할 수 없다. 전송 성공 유무는 서버콜백 기능을 이용하여야 한다.
                    Log.e("KAKAOTALK SUCCESS", "")
                }
            })


    }

    fun isItemDataOK(item: TMapPOIItem): Boolean {
        return item.poiName != null && item.upperAddrName != null && item.poiPoint != null
    }

    fun itemFilter(item: TMapPOIItem): LocationData {

        var address = ""
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
        val locationData = LocationData(
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