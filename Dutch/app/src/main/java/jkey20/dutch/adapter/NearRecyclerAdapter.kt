package jkey20.dutch.adapter

import android.content.ActivityNotFoundException
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.LiveData
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.kakao.sdk.common.util.KakaoCustomTabsClient
import com.kakao.sdk.link.LinkClient
import com.kakao.sdk.link.WebSharerClient
import com.kakao.sdk.template.model.Button
import com.kakao.sdk.template.model.Content
import com.kakao.sdk.template.model.FeedTemplate
import com.kakao.sdk.template.model.Link
import jkey20.dutch.databinding.ItemNearBinding
import jkey20.dutch.model.LocationData

class NearRecyclerAdapter() :
    RecyclerView.Adapter<NearRecyclerAdapter.NearViewHolder>() {


    private var locationDataList = ArrayList<LocationData>()

    fun setLocationDataList(list: ArrayList<LocationData>) {
        locationDataList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NearViewHolder {
        val binding =
            ItemNearBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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

    class NearViewHolder(private val binding: ItemNearBinding) :
        RecyclerView.ViewHolder(binding.root) {

        var arrowButton = binding.rightArrowButton
        fun bind(locationData: LocationData) {
            binding.locationdata = locationData
        }
    }


    fun shareKakao(name: String, address: String, location: LocationData, context: Context) {

        val kakaoAddressResult = address.replace(" ", "%20")

        val feed = FeedTemplate(
            content = Content(
                title = name,
                description = "중간지점을 확인해보세요!\n" +
                        "주소 : $address",
                imageUrl = "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2Ft3sEu%2FbtqJKvIDbb0%2FNOg8Ozhw0IgWkpagWecdr1%2Fimg.png",
                link = Link(
                    webUrl = "https://map.kakao.com/link/map/$kakaoAddressResult," + location.lat + "," + location.lon,
                    mobileWebUrl = "https://map.kakao.com/link/map/$kakaoAddressResult," + location.lat + "," + location.lon
                )

            )
        )

        if (LinkClient.instance.isKakaoLinkAvailable(context)) {
            LinkClient.instance.defaultTemplate(context, feed) { linkResult, error ->
                if (error != null) {
                    Log.e("KaKao Link Send Fail", "카카오링크 보내기 실패", error)
                }
                else if(linkResult != null){
                    Log.i("KaKao Link Send Success", "카카오링크 보내기 성공" + linkResult.intent)
                    context.startActivity(linkResult.intent)
                    Log.w("warning", "Warning Msg: ${linkResult.warningMsg}")
                    Log.w("warning", "Argument Msg: ${linkResult.argumentMsg}")
                }

            }
        }
        else{
            val sharerUrl = WebSharerClient.instance.defaultTemplateUri(feed)
            try{
                KakaoCustomTabsClient.openWithDefault(context, sharerUrl)
            }catch (e : UnsupportedOperationException){
                Log.i("unsupport chrome", "!")
            }
            try{
                KakaoCustomTabsClient.open(context, sharerUrl)
            }catch (e : ActivityNotFoundException){
                Log.i("unsupport internetbrowser", "!")
            }
        }


    }
}
