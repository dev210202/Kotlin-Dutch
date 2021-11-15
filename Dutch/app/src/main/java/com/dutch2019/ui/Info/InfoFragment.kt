package com.dutch2019.ui.Info

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.viewModels
import com.dutch2019.base.BaseFragment
import com.skt.Tmap.TMapMarkerItem
import com.skt.Tmap.TMapPoint
import com.skt.Tmap.TMapView
import com.dutch2019.BuildConfig
import com.dutch2019.R
import com.dutch2019.databinding.FragmentInfoBinding
import com.dutch2019.model.LocationData
import com.dutch2019.util.filtBlank
import com.dutch2019.util.filtDoubleBlank


class InfoFragment : BaseFragment<FragmentInfoBinding>(
    R.layout.fragment_info
) {
    private val infoViewModel : InfoViewModel by viewModels()
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        InfoFragmentArgs.fromBundle(requireArguments()).let { data ->
            infoViewModel.setLocationData(data.locationdata)
            binding.name = filtDoubleBlank(infoViewModel.locationData.value!!.name)
            binding.address = filtDoubleBlank(infoViewModel.locationData.value!!.address)
            binding.tel = "전화번호 : " + filtBlank(infoViewModel.locationData.value!!.tel)
            binding.infoMaplayout.addView(mapSetting(infoViewModel.getLocationData()))
        }
        binding.layoutWeb.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://m.search.naver.com/search.naver?where=nexearch&sm=top_hty&fbm=1&ie=utf8&query=${binding.name}"))
            startActivity(intent)
        }
        binding.layoutShare.setOnClickListener { view->
            infoViewModel.shareKakao(binding.name!!, binding.address!!, infoViewModel.getLocationData(), requireContext())
        }

    }
    fun mapSetting(data: LocationData): TMapView {
        val markerItemPoint = TMapPoint(data.lat, data.lon)

        val markerImage =
            BitmapFactory.decodeResource(
                requireContext().resources,
                R.drawable.ic_marker_black
            )

        val markerItem = TMapMarkerItem().apply {
            icon = markerImage
            tMapPoint = markerItemPoint
            setPosition(0.5F, 0.8F)
        }
        return TMapView(context).apply {
            setSKTMapApiKey("${BuildConfig.T_MAP_API}")
            setCenterPoint(data.lon, data.lat)
            addMarkerItem("markerItem", markerItem)
        }
    }
}