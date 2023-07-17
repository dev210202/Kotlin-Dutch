package com.dutch2019.ui.near

import android.graphics.PointF
import android.os.Bundle
import android.view.View
import android.view.View.*
import android.widget.Button
import androidx.fragment.app.activityViewModels
import com.dutch2019.R
import com.dutch2019.adapter.EmptyDataObserver
import com.dutch2019.adapter.NearRecyclerAdapter
import com.dutch2019.base.BaseFragment
import com.dutch2019.databinding.FragmentNearBinding
import com.dutch2019.ui.middle.MiddleViewModel
import com.dutch2019.util.*
import com.dutch2019.util.marker.*
import com.skt.Tmap.TMapView

class NearFragment : BaseFragment<FragmentNearBinding>(R.layout.fragment_near) {
    private val vm: MiddleViewModel by activityViewModels()
    private val emptyDataObserver by lazy {
        EmptyDataObserver(binding.rvNearFacility, binding.tvEmpty)
    }
    private val nearRecyclerAdapter by lazy {
        NearRecyclerAdapter(onItemClicked = { locationData ->
            removeAllBallon(tMapView)
            val clickedItem = tMapView.getMarkerItem2FromID(locationData.name)
            clickedItem.onSingleTapUp(PointF(), tMapView)
        }).apply {
            registerAdapterDataObserver(emptyDataObserver)
        }
    }
    private val chipOnClickListener by lazy {
        ChipOnClickListener()
    }
    lateinit var tMapView: TMapView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //showLoadingDialog(requireActivity())
        tMapView = TMapView(context)
        markLocationList(tMapView, requireContext(), vm.getLocationList())
        markMiddleLocation(tMapView, requireContext(), vm.getCenterPoint())
        markRatioLocation(tMapView, requireContext(), vm.getRatioPoint())
        mapAutoZoom(tMapView, vm.getLocationList(), vm.getSearchPoint())
        setMarkerClickEvent(tMapView)

        binding.layoutNear.addView(tMapView)

        vm.facilityList.observe(viewLifecycleOwner) { list ->
            if (list.isEmpty()) {
                setEmptyViewVisible()
            }
            binding.rvNearFacility.adapter = nearRecyclerAdapter
            nearRecyclerAdapter.setLocationDataList(list)
            markNearFacilityList(tMapView, requireContext(), list)
        }
        binding.btnTransport.setOnClickListener(chipOnClickListener)
        binding.btnCulture.setOnClickListener(chipOnClickListener)
        binding.btnFood.setOnClickListener(chipOnClickListener)
        binding.btnCafe.setOnClickListener(chipOnClickListener)
    }

    inner class ChipOnClickListener : OnClickListener {
        override fun onClick(view: View) {
            setButtonsStateDefault()
            setButtonState(view as Button, ButtonState.ACTIVE)
            removeAllNearFacilityMark(tMapView, vm.getFacilityList())
            setEmptyViewInvisible()
            when (view) {
                binding.btnTransport -> {
                    vm.searchNearFacility(
                        vm.getSearchPoint(),
                        getFacilitySearchCategory(Category.TRANSPORT)
                    )
                }
                binding.btnFood -> {
                    vm.searchNearFacility(
                        vm.getSearchPoint(),
                        getFacilitySearchCategory(Category.FOOD)
                    )
                }
                binding.btnCafe -> {
                    vm.searchNearFacility(
                        vm.getSearchPoint(),
                        getFacilitySearchCategory(Category.CAFE)
                    )
                }
                binding.btnCulture -> {
                    vm.searchNearFacility(
                        vm.getSearchPoint(),
                        getFacilitySearchCategory(Category.CULTURE)
                    )
                }
            }
        }
    }

    private fun setButtonsStateDefault() {
        setButtonState(binding.btnTransport, ButtonState.DEFAULT)
        setButtonState(binding.btnCafe, ButtonState.DEFAULT)
        setButtonState(binding.btnFood, ButtonState.DEFAULT)
        setButtonState(binding.btnCulture, ButtonState.DEFAULT)
    }

    private fun setMarkerClickEvent(tMapView: TMapView) {
        tMapView.setOnMarkerClickEvent { _, tMapMarkerItem ->
            if (isNotLocationMarker(tMapMarkerItem.id, vm.getLocationList()) && isNotMiddleMarker(
                    tMapMarkerItem.id
                )
            ) {
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
            } else {
                val clickedPoint = tMapMarkerItem.tMapPoint
                vm.setSearchPoint(clickedPoint)
                tMapView.setCenterPoint(clickedPoint.longitude, clickedPoint.latitude)
            }
        }
    }

    private fun setEmptyViewVisible() {
        binding.tvEmpty.visibility = VISIBLE
    }

    private fun setEmptyViewInvisible() {
        binding.tvEmpty.visibility = INVISIBLE
    }
}
