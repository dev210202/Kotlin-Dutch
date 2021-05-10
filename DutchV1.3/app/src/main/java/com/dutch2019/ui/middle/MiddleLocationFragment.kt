package com.dutch2019.ui.middle

import android.os.Bundle
import androidx.lifecycle.Observer
import com.dutch2019.R
import com.dutch2019.base.BaseFragment
import com.dutch2019.databinding.FragmentMiddleLocationBinding


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
            binding.middleaddresstextview.text = it
        })
        viewModel.nearStationName.observe(this, Observer {
            binding.nearsubwaytextview.text = it
        })

    }
}