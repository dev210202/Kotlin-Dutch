package com.dutch2019.ui.near

import android.graphics.PointF
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.dutch2019.base.BaseFragment
import com.dutch2019.R
import com.dutch2019.adapter.NearRecyclerAdapter
import com.dutch2019.databinding.FragmentNearBinding
import com.dutch2019.ui.middle.MiddleViewModel
import com.dutch2019.util.*
import com.dutch2019.util.marker.*
import com.skt.Tmap.TMapMarkerItem
import com.skt.Tmap.TMapMarkerItem2
import com.skt.Tmap.TMapPoint
import com.skt.Tmap.TMapView
import com.skt.Tmap.TMapView.OnCalloutMarker2ClickCallback
import com.skt.Tmap.TMapView.OnCalloutRightButtonClickCallback
import com.skt.Tmap.poi_item.TMapPOIItem
import java.util.ArrayList

class NearFragment : BaseFragment<FragmentNearBinding>(
    R.layout.fragment_near
) {
    private val vm: MiddleViewModel by activityViewModels()
    private val tMapView by lazy { TMapView(context) }
    private val nearRecyclerAdapter by lazy {
        NearRecyclerAdapter(
            onItemClicked = {

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
        setBallonOverlayClickEvent(tMapView)

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


    private fun setBallonOverlayClickEvent(tMapView: TMapView) {
        tMapView.setOnCalloutRightButtonClickListener {
            Log.e("RIGHTBUTTON", it.toString())
        }
        tMapView.setOnClickListenerCallBack(object : OnClickListener,
            TMapView.OnClickListenerCallback {
            override fun onClick(p0: View?) {
            }

            override fun onPressEvent(
                p0: ArrayList<TMapMarkerItem>?,
                p1: ArrayList<TMapPOIItem>?,
                point: TMapPoint,
                p3: PointF?
            ): Boolean {
                if(vm.isMarkClick(point)){
                    vm.setSearchPoint(point)
                    tMapView.setCenterPoint(point.longitude, point.latitude)
                    nearRecyclerAdapter.setSelectedPosition(vm.getIndexToFacilityList(point) + 1)
                }
                else{
                    Log.e("NOT MARK","!!")
                }
                return true
            }

            override fun onPressUpEvent(
                p0: ArrayList<TMapMarkerItem>?,
                p1: ArrayList<TMapPOIItem>?,
                p2: TMapPoint?,
                p3: PointF?
            ): Boolean {
                return true
            }

        })
    }
}
