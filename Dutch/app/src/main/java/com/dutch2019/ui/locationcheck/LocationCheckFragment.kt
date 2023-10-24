package com.dutch2019.ui.locationcheck

import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.dutch2019.R
import com.dutch2019.base.BaseFragment
import com.dutch2019.databinding.FragmentLocationCheckBinding
import com.dutch2019.model.LocationData
import com.dutch2019.ui.LocationViewModel
import com.dutch2019.ui.RecentSearchViewModel
import com.dutch2019.ui.main.MainViewModel
import com.dutch2019.util.marker.markLocationCheck
import com.skt.Tmap.TMapView


class LocationCheckFragment : BaseFragment<FragmentLocationCheckBinding>(
    R.layout.fragment_location_check
) {
    private val vm: LocationViewModel by activityViewModels()
    private val recentSearchViewModel : RecentSearchViewModel by activityViewModels()
    private lateinit var locationData: LocationData
    private lateinit var tMapView: TMapView
    private var selectedItemIndex = 0
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initReceivedData()
        initTMapView()
        initButtonSetLocation()
        initButtonLeftArrow()
    }

    private fun initButtonLeftArrow() {
        OnClickListener { findNavController().popBackStack() }.apply {
            binding.layoutIbLeftArrow.setOnClickListener(this)
            binding.ibLeftArrow.setOnClickListener(this)
        }
    }

    private fun initButtonSetLocation() {
        binding.btnSetLocation.setOnClickListener {
            locationData.copy(id = 0).apply {
                vm.changeLocationListItem(selectedItemIndex, this)
                recentSearchViewModel.saveSearchDataIntoDB(this)
                recentSearchViewModel.addDBData(this)
            }
            findNavController().navigate(LocationCheckFragmentDirections.actionLocationCheckFragmentToMainFragment())
        }
    }

    private fun initTMapView() {
        tMapView = TMapView(context).apply {
            this.setCenterPoint(locationData.lon, locationData.lat)
            markLocationCheck(this, requireContext(), locationData.convertTMapPoint())
            binding.layoutCheckMap.addView(this)
        }
    }

    private fun initReceivedData() {
        LocationCheckFragmentArgs.fromBundle(requireArguments()).let { data ->
            locationData = data.locationData
            binding.name = data.locationData.name
            binding.address = data.locationData.address
            selectedItemIndex = data.selectedItemIndex
        }
    }
}