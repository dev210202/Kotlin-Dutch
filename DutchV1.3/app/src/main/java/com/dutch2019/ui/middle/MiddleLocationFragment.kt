package com.dutch2019.ui.middle

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.dutch2019.R
import com.dutch2019.adapter.SearchRecyclerAdapter
import com.dutch2019.base.BaseFragment
import com.dutch2019.databinding.FragmentMiddleLocationBinding
import com.dutch2019.ui.locationcheck.LocationCheckFragmentArgs
import com.dutch2019.ui.main.MainViewModel
import com.dutch2019.ui.search.SearchLocationFragmentArgs
import com.skt.Tmap.TMapTapi
import com.skt.Tmap.TMapView


class MiddleLocationFragment : BaseFragment<FragmentMiddleLocationBinding, MiddleLocationViewModel>(
    R.layout.fragment_middle_location,
    MiddleLocationViewModel::class.java
) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var locationInfo = MiddleLocationFragmentArgs.fromBundle(requireArguments()).let { data ->
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