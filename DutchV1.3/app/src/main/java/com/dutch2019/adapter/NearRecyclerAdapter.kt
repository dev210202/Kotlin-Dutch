package com.dutch2019.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.location_list_item.view.*
import com.dutch2019.R
import com.dutch2019.base.BaseViewModel
import com.dutch2019.databinding.LocationListItemBinding
import com.dutch2019.databinding.NearFacilityListItemBinding
import com.dutch2019.model.LocationInfo
import com.dutch2019.ui.search.SearchLocationFragmentDirections
import com.kakao.kakaolink.v2.KakaoLinkResponse
import com.kakao.kakaolink.v2.KakaoLinkService
import com.kakao.message.template.*
import com.kakao.network.ErrorResult
import com.kakao.network.callback.ResponseCallback

class NearRecyclerAdapter() :
    RecyclerView.Adapter<NearRecyclerAdapter.NearViewHolder>() {


    private var locationDataList = ArrayList<LocationInfo>()

    fun setLocationDataList(list: ArrayList<LocationInfo>) {
        locationDataList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NearViewHolder {
        val binding =
            NearFacilityListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NearViewHolder(binding)
    }

    override fun getItemCount(): Int = locationDataList.size


    override fun onBindViewHolder(holder: NearViewHolder, position: Int) {
        holder.bind(locationDataList[position])
        holder.arrowButton.setOnClickListener { view ->
            val name = locationDataList[position].name
            val address = locationDataList[position].address
            val locationInfo = locationDataList[position]
           // shareLocation(name, address, locationInfo, view.context)
            shareKakao(name, address, locationInfo, view.context)
        }
    }

    class NearViewHolder(private val binding: NearFacilityListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        var arrowButton = binding.rightArrowButton
        fun bind(locationInfo: LocationInfo) {
            binding.locationinfo = locationInfo
        }
    }
}

fun shareKakao(name: String, address: String, location: LocationInfo, context: Context) {

    val kakaoAddressResult = address.replace(" ", "%20")
    val params = FeedTemplate.newBuilder(
        ContentObject.newBuilder(
            name,
            "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2Ft3sEu%2FbtqJKvIDbb0%2FNOg8Ozhw0IgWkpagWecdr1%2Fimg.png",
            LinkObject.newBuilder()
                .setWebUrl("https://map.kakao.com/link/map/$kakaoAddressResult," + location.latitude + "," + location.longitude)
                .setMobileWebUrl("https://map.kakao.com/link/map/$kakaoAddressResult," + location.latitude + "," + location.longitude)
                .build()
        ).setDescrption("중간지점을 확인해보세요!\n주소 : $address").build()
    )
        .addButton(
            ButtonObject(
                "위치 보기", // 왼쪽 버튼의 표시될 텍스트를 설정
                LinkObject.newBuilder()
                    .setWebUrl("https://map.kakao.com/link/map/$kakaoAddressResult," + location.latitude + "," + location.longitude)
                    .setMobileWebUrl("https://map.kakao.com/link/map/$kakaoAddressResult," + location.latitude + "," + location.longitude)
                    .build()
            )
        )
        .build()

    var serverCallbackArgs = mutableMapOf<String, String>()
    serverCallbackArgs.put("user_id", "current_user_id")
    serverCallbackArgs.put("product_id", "shared_product_id")

    KakaoLinkService.getInstance().sendDefault(context, params, serverCallbackArgs,
        object : ResponseCallback<KakaoLinkResponse?>() {
            override fun onFailure(errorResult: ErrorResult) {
                Log.e("ERROR", "!!")
            }

            override fun onSuccess(result: KakaoLinkResponse?) {
                // 템플릿 밸리데이션과 쿼터 체크가 성공적으로 끝남. 톡에서 정상적으로 보내졌는지 보장은 할 수 없다. 전송 성공 유무는 서버콜백 기능을 이용하여야 한다.
                Log.e("KAKAOTALK SUCCESS", "")
            }
        })



}
/*
fun shareLocation(name: String, address: String, location: LocationInfo, context: Context) {
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
                Log.e("ERROR", "!!")
            }

            override fun onSuccess(result: KakaoLinkResponse?) {
                // 템플릿 밸리데이션과 쿼터 체크가 성공적으로 끝남. 톡에서 정상적으로 보내졌는지 보장은 할 수 없다. 전송 성공 유무는 서버콜백 기능을 이용하여야 한다.
                Log.e("KAKAOTALK SUCCESS", "")
            }
        })


}

 */