package com.dutch2019.ui.near

import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.dutch2019.base.BaseFragment
import com.dutch2019.R
import com.dutch2019.adapter.NearRecyclerAdapter
import com.dutch2019.databinding.FragmentNearBinding
import com.dutch2019.ui.middle.MiddleViewModel
import com.dutch2019.util.*
import com.dutch2019.util.marker.mapAutoZoom
import com.dutch2019.util.marker.markLocationList
import com.dutch2019.util.marker.markMiddleLocation
import com.dutch2019.util.marker.setMarkRatioLocation
import com.skt.Tmap.TMapView

class NearFragment : BaseFragment<FragmentNearBinding>(
    R.layout.fragment_near
) {
    private val vm: MiddleViewModel by activityViewModels()
    private val tMapView by lazy { TMapView(context) }
    private val nearRecyclerAdapter by lazy {
        NearRecyclerAdapter().apply {
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
        binding.layoutNear.addView(tMapView)


        vm.facilityList.observe(viewLifecycleOwner) { list ->
            binding.rvNearFacility.adapter = nearRecyclerAdapter
            nearRecyclerAdapter.setLocationDataList(list)
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
            when(view){
                binding.btnTransport ->{
                    vm.searchNearFacility(
                        vm.getSearchPoint(),
                        getFacilitySearchCategory(category.TRANSPORT)
                    )
                }
                binding.btnFood ->{
                    vm.searchNearFacility(
                        vm.getSearchPoint(),
                        getFacilitySearchCategory(category.FOOD)
                    )
                }
                binding.btnCafe ->{
                    vm.searchNearFacility(
                        vm.getSearchPoint(),
                        getFacilitySearchCategory(category.CAFE)
                    )
                }
                binding.btnCulture ->{
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

    private fun searchNearFacility(view: View, viewModel: NearViewModel) {
        when (view) {
            binding.btnTransport -> {
                viewModel.searchNearFacility(
                    viewModel.searchPoint,
                    viewModel.setNearFacilityCategory("대중교통")
                )
            }
            binding.btnCulture -> {
                viewModel.searchNearFacility(
                    viewModel.searchPoint,
                    viewModel.setNearFacilityCategory("문화시설")
                )
            }
            binding.btnFood -> {
                viewModel.searchNearFacility(
                    viewModel.searchPoint,
                    viewModel.setNearFacilityCategory("음식점")
                )
            }
            binding.btnCafe -> {
                viewModel.searchNearFacility(
                    viewModel.searchPoint,
                    viewModel.setNearFacilityCategory("카페")
                )
            }
        }
    }
}
