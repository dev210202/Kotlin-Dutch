package com.dutch2019.ui.locationcheck

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.dutch2019.base.BaseFragment
import com.skt.Tmap.TMapMarkerItem
import com.skt.Tmap.TMapPoint
import com.skt.Tmap.TMapView
import com.dutch2019.BuildConfig
import com.dutch2019.R
import com.dutch2019.databinding.FragmentLocationCheckBinding
import com.dutch2019.ui.main.MainViewModel
import com.dutch2019.model.LocationData


class LocationCheckFragment : BaseFragment<FragmentLocationCheckBinding>(
    R.layout.fragment_location_check
) {
    private val vm: MainViewModel by activityViewModels()
    lateinit var locationData: LocationData

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        LocationCheckFragmentArgs.fromBundle(requireArguments()).let { data ->
            locationData = data.locationData
            binding.layoutCheckMap.addView(mapSetting(locationData))
            binding.name = data.locationData.name
            binding.address = data.locationData.address
        }

        binding.btnSetLocation.setOnClickListener {
            vm.changeLocationListItem(vm.getSelectedItemIndex(), locationData)
            findNavController().navigate(LocationCheckFragmentDirections.actionLocationCheckFragmentToMainFragment())

        }
        binding.ibLeftArrow.setOnClickListener {
            findNavController().popBackStack()
        }
    }


    fun mapSetting(data: LocationData): TMapView {
        val markerItemPoint = TMapPoint(data.lat, data.lon)

        val drawable = ContextCompat.getDrawable(requireContext(), R.drawable.ic_marker_check)
        val bitmap = drawable!!.toBitmap()

        val markerItem = TMapMarkerItem().apply {
            icon = bitmap
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