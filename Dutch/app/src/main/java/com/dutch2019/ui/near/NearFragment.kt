package com.dutch2019.ui.near

import android.graphics.PointF
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import androidx.fragment.app.activityViewModels
import com.dutch2019.R
import com.dutch2019.adapter.NearRecyclerAdapter
import com.dutch2019.base.BaseFragment
import com.dutch2019.databinding.FragmentNearBinding
import com.dutch2019.ui.middle.MiddleViewModel
import com.dutch2019.util.category
import com.dutch2019.util.getFacilitySearchCategory
import com.dutch2019.util.marker.*
import com.dutch2019.util.setActiveButton
import com.skt.Tmap.TMapView

class NearFragment : BaseFragment<FragmentNearBinding>(
    R.layout.fragment_near
) {
    private val vm: MiddleViewModel by activityViewModels()
    private val tMapView by lazy { TMapView(context) }
    private val nearRecyclerAdapter by lazy {
        NearRecyclerAdapter(
            onItemClicked = { locationData ->
                removeAllBallon(tMapView)
                val clickedItem = tMapView.getMarkerItem2FromID(locationData.name)
                clickedItem.onSingleTapUp(PointF(), tMapView)
            }).apply {
            // add empty observer
        }
    }
    private val chipOnClickListener by lazy {
        ChipOnClickListener()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //showLoadingDialog(requireActivity())

        markLocationList(tMapView, requireContext(), vm.getLocationList())
        markMiddleLocation(tMapView, requireContext(), vm.getCenterPoint())
        setMarkRatioLocation(tMapView, requireContext(), vm.getRatioPoint())
        mapAutoZoom(tMapView, vm.getLocationList(), vm.getSearchPoint(), requireContext())
        setMarkerClickEvent(tMapView)

        binding.layoutNear.addView(tMapView)

        vm.facilityList.observe(viewLifecycleOwner) { list ->
            binding.rvNearFacility.adapter = nearRecyclerAdapter
            nearRecyclerAdapter.setLocationDataList(list)
            markNearFacilityList(tMapView, requireContext(), list)
        }
        binding.btnTransport.setOnClickListener(chipOnClickListener)
        binding.btnCulture.setOnClickListener(chipOnClickListener)
        binding.btnFood.setOnClickListener(chipOnClickListener)
        binding.btnCafe.setOnClickListener(chipOnClickListener)
    }

    inner class ChipOnClickListener() : OnClickListener {
        override fun onClick(view: View) {
            setButtonStateDefault()
            setActiveButton(view as Button)
            removeNearFacilityMarks(tMapView, vm.getFacilityList())
            when (view) {
                binding.btnTransport -> {
                    vm.searchNearFacility(
                        vm.getSearchPoint(),
                        getFacilitySearchCategory(category.TRANSPORT)
                    )
                }
                binding.btnFood -> {
                    vm.searchNearFacility(
                        vm.getSearchPoint(),
                        getFacilitySearchCategory(category.FOOD)
                    )
                }
                binding.btnCafe -> {
                    vm.searchNearFacility(
                        vm.getSearchPoint(),
                        getFacilitySearchCategory(category.CAFE)
                    )
                }
                binding.btnCulture -> {
                    vm.searchNearFacility(
                        vm.getSearchPoint(),
                        getFacilitySearchCategory(category.CULTURE)
                    )
                }
            }
        }
    }

    fun setButtonStateDefault() {
        binding.btnTransport.isSelected = false
        binding.btnCafe.isSelected = false
        binding.btnFood.isSelected = false
        binding.btnCulture.isSelected = false
    }

    private fun setMarkerClickEvent(tMapView: TMapView) {
        tMapView.setOnMarkerClickEvent { _, tMapMarkerItem ->
            if(isNotLocationMarker(tMapMarkerItem.id, vm.getLocationList()) && isNotMiddleMarker(tMapMarkerItem.id)) {
                val clickedPoint = tMapMarkerItem.tMapPoint
                vm.setSearchPoint(clickedPoint)
                changeDefaultNearMarks(
                    tMapView,
                    requireContext(),
                    vm.getFacilityList(),
                    tMapMarkerItem
                )
                tMapView.setCenterPoint(clickedPoint.longitude, clickedPoint.latitude)
                changeNearPrimaryMark(tMapMarkerItem, requireContext())
                val index = vm.getIndexToFacilityList(clickedPoint, tMapMarkerItem.id)
                binding.rvNearFacility.scrollToPosition(index)
                nearRecyclerAdapter.setSelectedPosition(index)
            }
            else{
                val clickedPoint = tMapMarkerItem.tMapPoint
                vm.setSearchPoint(clickedPoint)
                tMapView.setCenterPoint(clickedPoint.longitude, clickedPoint.latitude)
            }
        }
    }
}
