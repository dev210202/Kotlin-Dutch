package com.dutch2019.ui.middle

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.dutch2019.base.BaseFragment
import com.skt.Tmap.TMapView
import dagger.hilt.android.AndroidEntryPoint
import com.dutch2019.R
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
    private val tMapView by lazy { TMapView(context) }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = vm
        showLoadingDialog(requireActivity())

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

        binding.layoutMiddle.addView(tMapView)
        mapAutoZoom(tMapView, vm.getLocationList(), vm.getCenterPoint())

        binding.btnRatio.setOnClickListener { view ->
            view.findNavController().navigate(
                MiddleFragmentDirections.actionMiddleFragmentToRatioFragment(
                    LocationDataList().convertLocationData(vm.getLocationList())
                )
            )
        }

        binding.btnCheckNearfacility.setOnClickListener { view ->
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

        tMapView.setOnMarkerClickEvent { _, p1 ->
            val point = p1.tMapPoint
            vm.setSearchPoint(point)
            viewModel.setCenterPointAddress(point)
            viewModel.setCenterPointNearSubway(point)
            binding.tvInfo.text = p1.id
            when (p1.id) {
                MarkerId.MIDDLE -> {
                    binding.tvInfo.setTextColor(
                        ContextCompat.getColor(tMapView.rootView.context, R.color.orange)
                    )
                    viewModel.resetRouteTime()
                }
                MarkerId.RATIO -> {
                    binding.tvInfo.setTextColor(
                        ContextCompat.getColor(tMapView.rootView.context, R.color.blue)
                    )
                    viewModel.setRouteTime(viewModel.getCenterPoint(), p1.latitude, p1.longitude)
                }
                else -> {
                    binding.tvInfo.setTextColor(
                        ContextCompat.getColor(tMapView.rootView.context, R.color.gray2)
                    )
                    viewModel.setRouteTime(viewModel.getCenterPoint(), p1.latitude, p1.longitude)
                }
            }
        }

    }


}