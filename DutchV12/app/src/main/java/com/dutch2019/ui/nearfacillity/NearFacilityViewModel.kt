package com.dutch2019.ui.nearfacillity

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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
import com.dutch2019.data.LocationData
import java.util.*

class NearFacilityViewModel : ViewModel() {
    var locationList = MutableLiveData<ArrayList<LocationData>>()
    lateinit var locationArrayList: ArrayList<LocationData>
    var errorMessage = MutableLiveData<String>()
    fun searchNearTransport(centerPoint: TMapPoint) {
        val tMapData = TMapData()
        tMapData.findAroundNamePOI(
            centerPoint,
            "지하철;버스;버스정류장;",
            3,
            20
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

    fun searchNearCulture(centerPoint: TMapPoint) {
        val tMapData = TMapData()
        tMapData.findAroundNamePOI(
            centerPoint,
            "주요시설물;문화시설;영화관;놀거리;",
            3,
            20
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
            20
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
            20
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
                "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fk.kakaocdn.net%2Fdn%2FcN141f%2FbtqyK9q3rqe%2FK8UNSjqsKMBIT32jLFL1r1%2Fimg.png",
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
        return item.poiName != null && item.upperAddrName != null && item.middleAddrName != null && item.lowerAddrName != null && item.poiPoint != null
    }
}