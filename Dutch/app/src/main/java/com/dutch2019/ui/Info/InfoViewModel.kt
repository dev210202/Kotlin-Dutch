package com.dutch2019.ui.Info

import android.content.ActivityNotFoundException
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dutch2019.base.BaseViewModel
import com.kakao.sdk.common.util.KakaoCustomTabsClient
import com.kakao.sdk.link.LinkClient
import com.kakao.sdk.link.WebSharerClient
import com.kakao.sdk.template.model.Button
import com.kakao.sdk.template.model.Content
import com.kakao.sdk.template.model.FeedTemplate
import com.kakao.sdk.template.model.Link
import com.dutch2019.model.LocationData



class InfoViewModel: BaseViewModel() {

    private val _locationData = MutableLiveData<LocationData>()
    val locationData: LiveData<LocationData> get() = _locationData

    fun setLocationData(data : LocationData){
        _locationData.value = data
    }

    fun getLocationData(): LocationData{
        return _locationData.value!!
    }

    fun shareKakao(name: String, address: String, location: LocationData, context: Context) {

        val kakaoAddressResult = address.replace(" ", "%20")

        val feedTemplate = FeedTemplate(
            content = Content(
                title = name,
                description = "중간지점을 확인해보세요!\n" +
                        "주소 : " + address,
                imageUrl = "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2Ft3sEu%2FbtqJKvIDbb0%2FNOg8Ozhw0IgWkpagWecdr1%2Fimg.png",
                link = Link(
                    webUrl = "https://map.kakao.com/link/map/$kakaoAddressResult," + location.lat + "," + location.lon,
                    mobileWebUrl = "https://map.kakao.com/link/map/$kakaoAddressResult," + location.lat + "," + location.lon
                )


            ),
            // todo : 카카오톡 공유하면 나오는 버튼
            buttons = listOf(
                Button(
                    "상세정보 보기",
                    Link(

                        webUrl = "https://map.kakao.com/link/map/$kakaoAddressResult," + location.lat + "," + location.lon,
                        mobileWebUrl = "https://map.kakao.com/link/map/$kakaoAddressResult," + location.lat + "," + location.lon

                    )
                )
            )
        )

        if (LinkClient.instance.isKakaoLinkAvailable(context)) {
            LinkClient.instance.defaultTemplate(context, feedTemplate) { linkResult, error ->
                if (error != null) {
                    // todo 1 : 카톡이 있는데 카카오링크가 안보내지는 경우 에러처리
                    Log.e("KaKao Link Send Fail", "카카오링크 보내기 실패", error)
                } else if (linkResult != null) {
                    Log.i("KaKao Link Send Success", "카카오링크 보내기 성공" + linkResult.intent)
                    context.startActivity(linkResult.intent)
                    Log.w("warning", "Warning Msg: ${linkResult.warningMsg}")
                    Log.w("warning", "Argument Msg: ${linkResult.argumentMsg}")
                }

            }
        } else {
            val sharerUrl = WebSharerClient.instance.defaultTemplateUri(feedTemplate)
            try {
                KakaoCustomTabsClient.openWithDefault(context, sharerUrl)
            } catch (e: UnsupportedOperationException) {
                // todo 2 : 카톡이없고 카카오링크가 안보내지는 경우 에러처리
                Log.i("unsupport chrome", "!")
            }
            try {
                KakaoCustomTabsClient.open(context, sharerUrl)
            } catch (e: ActivityNotFoundException) {
                Log.i("unsupport internetbrowser", "!")
            }
        }


    }
}