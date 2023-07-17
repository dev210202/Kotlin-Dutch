package com.dutch2019.ui.middle

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.skt.Tmap.TMapView
import dagger.hilt.android.AndroidEntryPoint
import com.dutch2019.R
import com.dutch2019.base.BaseFragment
import com.dutch2019.base.LifeCycleFragment
import com.dutch2019.databinding.FragmentMiddleBinding
import com.dutch2019.model.LocationDataList
import com.dutch2019.util.MarkerId
import com.dutch2019.util.dismissLoadingDialog
import com.dutch2019.util.marker.*
import com.dutch2019.util.showLoadingDialog

@AndroidEntryPoint
class MiddleFragment : BaseFragment<FragmentMiddleBinding>(
    R.layout.fragment_middle
) {
    private val vm: MiddleViewModel by activityViewModels()
    private lateinit var tMapView: TMapView
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tMapView = TMapView(context)
        binding.vm = vm

        showLoadingDialog(requireActivity())
        binding.layoutMiddle.addView(tMapView)

        MiddleFragmentArgs.fromBundle(requireArguments()).let { data ->
            vm.setLocationList(data.locationlist)
        }

        vm.setCenterPoint(vm.calculateCenterPoint(vm.getLocationList()))
        vm.setSearchPoint(vm.getCenterPoint())

        markLocationList(tMapView, requireContext(), vm.getLocationList())
        markMiddleLocation(tMapView, requireContext(), vm.getCenterPoint())
        markRatioLocation(tMapView, requireContext(), vm.getRatioPoint())
        setBallonOverlayClickEvent(tMapView, vm)

        vm.setCenterPointAddress(vm.getCenterPoint())
        vm.setCenterPointNearSubway(vm.getCenterPoint())


        mapAutoZoom(tMapView, vm.getLocationList(), vm.getCenterPoint())

        binding.btnRatio.setOnClickListener { view ->
            view.findNavController().navigate(
                MiddleFragmentDirections.actionMiddleFragmentToRatioFragment(
                    LocationDataList().convertLocationData(vm.getLocationList())
                )
            )
        }

        binding.btnCheckNearfacility.setOnClickListener { view ->
            binding.layoutMiddle.removeView(tMapView)
            view.findNavController().navigate(
                MiddleFragmentDirections.actionMiddleFragmentToNearFragment()
            )
        }

        binding.ibLeftArrow.setOnClickListener {
            findNavController().popBackStack()
        }

        vm.centerPointAddress.observe(viewLifecycleOwner, Observer {
            vm.saveLocations(vm.getLocationList())
            dismissLoadingDialog()
        })

    }

    private fun setBallonOverlayClickEvent(tMapView: TMapView, viewModel: MiddleViewModel) {

        tMapView.setOnMarkerClickEvent { _, tMapMarkerItem2 ->
            val point = tMapMarkerItem2.tMapPoint
            vm.setSearchPoint(point)
            viewModel.setCenterPointAddress(point)
            viewModel.setCenterPointNearSubway(point)
            binding.tvInfo.text = tMapMarkerItem2.id
            when (tMapMarkerItem2.id) {
                MarkerId.MIDDLE -> {
                    (tMapMarkerItem2 as MarkerOverlay).changeTextPrimaryColor(requireContext())
                    binding.tvInfo.setTextColor(
                        ContextCompat.getColor(tMapView.rootView.context, R.color.orange)
                    )
                    viewModel.resetRouteTime()
                }
                MarkerId.RATIO -> {
                    (tMapMarkerItem2 as MarkerOverlay).changeTextBlueColor(requireContext())
                    binding.tvInfo.setTextColor(
                        ContextCompat.getColor(tMapView.rootView.context, R.color.skyblue)
                    )
                    viewModel.setRouteTime(
                        viewModel.getCenterPoint(),
                        tMapMarkerItem2.latitude,
                        tMapMarkerItem2.longitude
                    )
                }
                else -> {
                    (tMapMarkerItem2 as MarkerOverlay).changeTextDefaultColor(requireContext())
                    binding.tvInfo.setTextColor(
                        ContextCompat.getColor(tMapView.rootView.context, R.color.gray2)
                    )
                    viewModel.setRouteTime(
                        viewModel.getCenterPoint(),
                        tMapMarkerItem2.latitude,
                        tMapMarkerItem2.longitude
                    )
                }
            }
        }

    }


}