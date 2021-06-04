package com.dutch2019.ui.middle

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.dutch2019.R
import com.dutch2019.base.BaseFragment
import com.dutch2019.databinding.FragmentMiddleLocationBinding
import com.dutch2019.ui.ratio.RatioViewModel


class MiddleLocationFragment : BaseFragment<FragmentMiddleLocationBinding, MiddleLocationViewModel>(
    R.layout.fragment_middle_location,
    MiddleLocationViewModel::class.java
) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        MiddleLocationFragmentArgs.fromBundle(requireArguments()).let { data ->
            viewModel.setLocationList(data.locationlist)
        }
        viewModel.middleLocationAddress.observe(this, Observer {
            binding.textviewMiddleAddress.text = it
        })
        viewModel.nearStationName.observe(this, Observer {
            binding.nearsubwaytextview.text = it
        })
        viewModel.totalRouteTime.observe(this, Observer {
            binding.routetitmeTextview.text = it
        })

    }
}