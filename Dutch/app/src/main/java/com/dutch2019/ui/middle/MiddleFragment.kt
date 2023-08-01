package com.dutch2019.ui.middle

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.skt.Tmap.TMapView
import dagger.hilt.android.AndroidEntryPoint
import com.dutch2019.R
import com.dutch2019.base.BaseFragment
import com.dutch2019.databinding.FragmentMiddleBinding
import com.dutch2019.model.LocationDataList
import com.dutch2019.util.*
import com.dutch2019.util.marker.*

@AndroidEntryPoint
class MiddleFragment : BaseFragment<FragmentMiddleBinding>(
    R.layout.fragment_middle
) {
    private val vm: MiddleViewModel by activityViewModels()
    private lateinit var tMapView: TMapView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showLoadingDialog(requireActivity())

        binding.vm = vm
        initLocationList()

        vm.setCenterPoint(calculateCenterPoint(vm.getLocationList()))
        vm.setSearchPoint(vm.getCenterPoint())
        vm.searchCenterPointAddress(vm.getCenterPoint())
        vm.setCenterPointNearSubway(vm.getCenterPoint())

        initTMapView()
        initButtonRatio()
        initButtonCheckNearFacility()
        initButtonLeftArrow()

        vm.centerPointAddress.observe(viewLifecycleOwner) { address ->
            if (address.isNotEmpty()) dismissLoadingDialog()
        }

    }

    private fun initButtonLeftArrow() {
        binding.ibLeftArrow.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun initButtonCheckNearFacility() {
        binding.btnCheckNearfacility.setOnClickListener {
            findNavController().navigate(
                MiddleFragmentDirections.actionMiddleFragmentToNearFragment()
            )
        }
    }

    private fun initButtonRatio() {
        binding.btnRatio.setOnClickListener {
            findNavController().navigate(
                MiddleFragmentDirections.actionMiddleFragmentToRatioFragment(
                    LocationDataList().convertLocationData(vm.getLocationList())
                )
            )
        }
    }

    private fun initTMapView() {
        tMapView = TMapView(context).apply {
            markLocationList(this, requireContext(), vm.getLocationList())
            markMiddleLocation(this, requireContext(), vm.getCenterPoint())
            markRatioLocation(this, requireContext(), vm.getRatioPoint())
            setBallonOverlayClickEvent(this)
            mapAutoZoom(this, vm.getLocationList(), vm.getCenterPoint())
            binding.layoutMiddle.addView(this)
        }
    }

    private fun initLocationList() {
        MiddleFragmentArgs.fromBundle(requireArguments()).let { data ->
            vm.setLocationList(data.locationlist.value)
        }
    }

    private fun setBallonOverlayClickEvent(tMapView: TMapView) {

        tMapView.setOnMarkerClickEvent { _, tMapMarkerItem2 ->
            tMapMarkerItem2.tMapPoint.apply {
                vm.setSearchPoint(this)
                vm.searchCenterPointAddress(this)
                vm.setCenterPointNearSubway(this)
            }

            binding.tvInfo.text = tMapMarkerItem2.id

            when (tMapMarkerItem2.id) {
                MarkerId.MIDDLE.value -> {
                    (tMapMarkerItem2 as MarkerOverlay).changeTextPrimaryColor(requireContext())
                    binding.tvInfo.setTextColor(Color.TEXT_PRIMARY.getColor(tMapView.rootView.context))
                    vm.resetRouteTime()
                }
                MarkerId.RATIO.value -> {
                    (tMapMarkerItem2 as MarkerOverlay).changeTextBlueColor(requireContext())
                    binding.tvInfo.setTextColor(Color.TEXT_RATIO.getColor(tMapView.rootView.context))
                    vm.setRouteTime(
                        startPoint = tMapMarkerItem2.tMapPoint, endPoint = vm.getCenterPoint()
                    )
                }
                else -> {
                    (tMapMarkerItem2 as MarkerOverlay).changeTextDefaultColor(requireContext())
                    binding.tvInfo.setTextColor(Color.TEXT_DEFAULT.getColor(tMapView.rootView.context))
                    vm.setRouteTime(
                        startPoint = tMapMarkerItem2.tMapPoint, endPoint = vm.getCenterPoint()
                    )
                }
            }
        }

    }
}