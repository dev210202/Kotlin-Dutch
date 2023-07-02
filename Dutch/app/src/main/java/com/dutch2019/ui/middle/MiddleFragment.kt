package com.dutch2019.ui.middle

import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import com.dutch2019.BuildConfig
import com.dutch2019.base.BaseFragment
import com.skt.Tmap.TMapView
import dagger.hilt.android.AndroidEntryPoint
import com.dutch2019.R
import com.dutch2019.databinding.FragmentMiddleBinding
import com.dutch2019.model.LocationData
import com.dutch2019.model.LocationDataList
import com.dutch2019.ui.main.MainViewModel
import com.dutch2019.util.dismissLoadingDialog
import com.dutch2019.util.marker.*
import com.dutch2019.util.showLoadingDialog
import com.skt.Tmap.TMapPoint
import com.skt.Tmap.TMapTapi

@AndroidEntryPoint
class MiddleFragment : BaseFragment<FragmentMiddleBinding>(
    R.layout.fragment_middle
) {
    private val vm: MiddleViewModel by activityViewModels()
    private val tMapView by lazy { TMapView(context) }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        showLoadingDialog(requireActivity())

        MiddleFragmentArgs.fromBundle(requireArguments()).let { data ->
            vm.setLocationList(data.locationlist)
        }

        vm.setCenterPoint(vm.calculateCenterPoint(vm.getLocationList()))

        markLocationList(tMapView, requireContext(), vm.getLocationList())
        markMiddleLocation(tMapView, requireContext(), vm.getCenterPoint())
        setMarkRatioLocation(tMapView, requireContext(), vm.getRatioPoint())
        setBallonOverlayClickEvent(tMapView, vm)

        vm.setCenterPointAddress(vm.getCenterPoint())
        vm.setCenterPointNearSubway(vm.getCenterPoint())

        mapAutoZoom(tMapView, vm.getLocationList(), vm.getCenterPoint(),  requireContext())

        binding.maplayoutMiddle.addView(tMapView)

        binding.buttonMiddleRatiosetting.setOnClickListener { view ->
            view.findNavController().navigate(
                MiddleFragmentDirections.actionMiddleFragmentToRatioFragment(
                    LocationDataList().convertLocationData(vm.getLocationList())
                )
            )
        }

        binding.buttonCheckNearfacility.setOnClickListener { view ->
            view.findNavController().navigate(
                MiddleFragmentDirections.actionMiddleFragmentToNearFragment(
                    vm.getCenterPoint().latitude.toFloat(),
                    vm.getCenterPoint().longitude.toFloat()
                )
            )

        }

        vm.centerPointAddress.observe(viewLifecycleOwner, Observer {
            binding.textviewMiddleAddress.text = it
            vm.saveLocations(vm.getLocationList())
            dismissLoadingDialog()
        })
        vm.centerPointNearSubway.observe(viewLifecycleOwner, Observer {
            binding.textviewMiddleNearsubway.text = it
        })

        vm.routeTime.observe(viewLifecycleOwner, Observer {
            binding.textviewMiddleRoutetime.text = it
        })
    }

    private fun setBallonOverlayClickEvent(tMapView: TMapView, viewModel: MiddleViewModel) {

        tMapView.setOnMarkerClickEvent { _, p1 ->
            val point = p1.tMapPoint
            viewModel.setCenterPointAddress(point)
            viewModel.setCenterPointNearSubway(point)
            binding.textviewMiddleResult.text = p1.id
            if (p1.id == "중간지점") {
                binding.textviewMiddleResult.setTextColor(
                    ContextCompat.getColor(tMapView.rootView.context, R.color.orange)
                )
                viewModel.resetRouteTime()
            } else if (p1.id == "비율변경지점") {
                binding.textviewMiddleResult.setTextColor(
                    ContextCompat.getColor(tMapView.rootView.context, R.color.blue)
                )
                viewModel.setRouteTime(viewModel.getCenterPoint(), p1.latitude, p1.longitude)
            } else {
                binding.textviewMiddleResult.setTextColor(
                    ContextCompat.getColor(tMapView.rootView.context, R.color.black)
                )
                viewModel.setRouteTime(viewModel.getCenterPoint(), p1.latitude, p1.longitude)
            }
        }

    }


}