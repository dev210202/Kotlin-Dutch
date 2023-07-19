package com.dutch2019.ui.locationcheck

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.dutch2019.R
import com.dutch2019.base.BaseFragment
import com.dutch2019.databinding.FragmentLocationCheckBinding
import com.dutch2019.model.LocationData
import com.dutch2019.ui.main.MainViewModel
import com.dutch2019.util.marker.markLocationCheck
import com.skt.Tmap.TMapView


class LocationCheckFragment : BaseFragment<FragmentLocationCheckBinding>(
    R.layout.fragment_location_check
) {
    private val vm: MainViewModel by activityViewModels()
    lateinit var locationData: LocationData
    lateinit var tMapView: TMapView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tMapView = TMapView(context)
        LocationCheckFragmentArgs.fromBundle(requireArguments()).let { data ->
            locationData = data.locationData
            binding.name = data.locationData.name
            binding.address = data.locationData.address
        }
        tMapView.setCenterPoint(locationData.lon, locationData.lat)
        markLocationCheck(tMapView, requireContext(), locationData.convertTMapPoint())
        binding.layoutCheckMap.addView(tMapView)

        binding.btnSetLocation.setOnClickListener {
            locationData = locationData.copy(id = 0)
            vm.changeLocationListItem(vm.getSelectedItemIndex(), locationData)
            vm.saveSearchDataIntoDB(locationData)
            vm.addSearchData(locationData)
            findNavController().navigate(LocationCheckFragmentDirections.actionLocationCheckFragmentToMainFragment())
        }
        binding.ibLeftArrow.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}