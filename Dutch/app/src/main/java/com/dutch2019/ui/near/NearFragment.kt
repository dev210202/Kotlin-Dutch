package com.dutch2019.ui.near

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.dutch2019.base.BaseFragment
import com.dutch2019.R
import com.dutch2019.adapter.NearRecyclerAdapter
import com.dutch2019.databinding.FragmentNearBinding

class NearFragment : BaseFragment<FragmentNearBinding>(
    R.layout.fragment_near
) {
    private val nearViewModel: NearViewModel by viewModels()
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        NearFragmentArgs.fromBundle(requireArguments()).let{ data ->
            nearViewModel.searchPoint.latitude = data.lat.toDouble()
            nearViewModel.searchPoint.longitude = data.lon.toDouble()
        }
        binding.recyclerviewNear.adapter = NearRecyclerAdapter()

        binding.buttonNearTransport.setOnClickListener{
            setButtonSelect(binding.buttonNearTransport)
            searchNearFacility(binding.buttonNearTransport, nearViewModel)
        }
        binding.buttonNearCulture.setOnClickListener{
            setButtonSelect(binding.buttonNearCulture)
            searchNearFacility(binding.buttonNearCulture, nearViewModel)
        }
        binding.buttonNearFood.setOnClickListener{
            setButtonSelect(binding.buttonNearFood)
            searchNearFacility(binding.buttonNearFood, nearViewModel)
        }
        binding.buttonNearCafe.setOnClickListener{
            setButtonSelect(binding.buttonNearCafe)
            searchNearFacility(binding.buttonNearCafe, nearViewModel)
        }

        nearViewModel.facilityList.observe(viewLifecycleOwner, Observer { list ->
            (binding.recyclerviewNear.adapter as NearRecyclerAdapter).setLocationDataList(list)
        })
    }

    private fun setButtonSelect(view: View) {
        when (view) {
            binding.buttonNearTransport -> {
                binding.buttonNearTransport.isSelected = true
                binding.buttonNearCulture.isSelected = false
                binding.buttonNearFood.isSelected = false
                binding.buttonNearCafe.isSelected = false
            }
            binding.buttonNearCulture -> {
                binding.buttonNearTransport.isSelected = false
                binding.buttonNearCulture.isSelected = true
                binding.buttonNearFood.isSelected = false
                binding.buttonNearCafe.isSelected = false
            }
            binding.buttonNearFood -> {
                binding.buttonNearTransport.isSelected = false
                binding.buttonNearCulture.isSelected = false
                binding.buttonNearFood.isSelected = true
                binding.buttonNearCafe.isSelected = false
            }
            binding.buttonNearCafe -> {
                binding.buttonNearTransport.isSelected = false
                binding.buttonNearCulture.isSelected = false
                binding.buttonNearFood.isSelected = false
                binding.buttonNearCafe.isSelected = true
            }
        }
    }

    private fun searchNearFacility(view: View, viewModel: NearViewModel) {
        when (view) {
            binding.buttonNearTransport -> {
                viewModel.searchNearFacility(viewModel.searchPoint, viewModel.setNearFacilityCategory("대중교통"))
            }
            binding.buttonNearCulture -> {
                viewModel.searchNearFacility(viewModel.searchPoint, viewModel.setNearFacilityCategory("문화시설"))
            }
            binding.buttonNearFood -> {
                viewModel.searchNearFacility(viewModel.searchPoint, viewModel.setNearFacilityCategory("음식점"))
            }
            binding.buttonNearCafe -> {
                viewModel.searchNearFacility(viewModel.searchPoint, viewModel.setNearFacilityCategory("카페"))
            }
        }
    }
}
