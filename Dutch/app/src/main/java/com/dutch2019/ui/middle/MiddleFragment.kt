package com.dutch2019.ui.middle

import android.app.ProgressDialog
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.dutch2019.BuildConfig
import com.dutch2019.base.BaseFragment
import com.skt.Tmap.TMapView
import dagger.hilt.android.AndroidEntryPoint
import com.dutch2019.R
import com.dutch2019.databinding.FragmentMiddleBinding
import com.dutch2019.model.LocationDataList
import com.dutch2019.util.getSafetyText
import com.dutch2019.util.marker.*
import com.skt.Tmap.TMapTapi

@AndroidEntryPoint
class MiddleFragment : BaseFragment<FragmentMiddleBinding>(
    R.layout.fragment_middle
) {
    private val middleViewModel: MiddleViewModel by activityViewModels()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val tmapApi = TMapTapi(requireContext())
        tmapApi.setSKTMapAuthentication("${BuildConfig.T_MAP_API}")

        MiddleFragmentArgs.fromBundle(requireArguments()).let { data ->
            middleViewModel.setLocationList(data.locationlist)
        }
        val loadingDialog = ProgressDialog(requireActivity())
        showLoadingDialog(loadingDialog)
        middleViewModel.setCenterPoint(middleViewModel.calculateCenterPoint(middleViewModel.getLocationList()))

        val tMapView = TMapView(context)
        markLocationList(tMapView, requireContext(), middleViewModel.getLocationList())
        markMiddleLocation(tMapView, requireContext(), middleViewModel.getCenterPoint())
        setMarkRatioLocation(tMapView, requireContext(), middleViewModel.getRatioPoint())
        setBallonOverlayClickEvent(tMapView, middleViewModel)

        middleViewModel.setCenterPointAddress(middleViewModel.getCenterPoint())
        middleViewModel.setCenterPointNearSubway(middleViewModel.getCenterPoint())

        mapAutoZoom(tMapView, middleViewModel.getLocationList(), middleViewModel.getCenterPoint())

        binding.maplayoutMiddle.addView(tMapView)

        binding.buttonMiddleRatiosetting.setOnClickListener { view ->
            view.findNavController().navigate(
                MiddleFragmentDirections.actionMiddleFragmentToRatioFragment(
                    LocationDataList().convertLocationData(middleViewModel.getLocationList())
                )
            )
        }

        binding.buttonCheckNearfacility.setOnClickListener { view ->
            view.findNavController().navigate(
                MiddleFragmentDirections.actionMiddleFragmentToNearFragment(
                    middleViewModel.getCenterPoint().latitude.toFloat(),
                    middleViewModel.getCenterPoint().longitude.toFloat()
                )
            )

        }

        middleViewModel.centerPointAddress.observe(viewLifecycleOwner, Observer {
            binding.textviewMiddleAddress.text = it
            middleViewModel.saveLocations(middleViewModel.getLocationList())
            dismissLoadingDialog(loadingDialog)
        })
        middleViewModel.centerPointNearSubway.observe(viewLifecycleOwner, Observer {
            binding.textviewMiddleNearsubway.text = it
        })

        middleViewModel.routeTime.observe(viewLifecycleOwner, Observer {
            binding.textviewMiddleRoutetime.text = it
        })
    }

    fun setBallonOverlayClickEvent(tMapView: TMapView, viewModel: MiddleViewModel) {

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
    fun showLoadingDialog(loadingDialog : ProgressDialog){
        loadingDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        loadingDialog.setMessage("로딩중입니다..")
        loadingDialog.show()
    }
    fun dismissLoadingDialog(loadingDialog : ProgressDialog){
        loadingDialog.dismiss()
    }
}