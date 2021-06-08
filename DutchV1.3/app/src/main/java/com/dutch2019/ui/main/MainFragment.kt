package com.dutch2019.ui.main

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import com.dutch2019.R
import com.dutch2019.adapter.MainRecyclerAdapter
import com.dutch2019.base.BaseFragment
import com.dutch2019.databinding.FragmentMainBinding
import com.dutch2019.model.LocationInfo


class MainFragment : BaseFragment<FragmentMainBinding, MainViewModel>(
    R.layout.fragment_main,
    MainViewModel::class.java
) {
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        checkSelectedLocationFromDB(this)

        viewModel.initDB(requireActivity().application)
        viewModel.locationList.observe(
            viewLifecycleOwner,
            Observer { list ->
                if (binding.recyclerview.adapter != null) {
                    (binding.recyclerview.adapter as MainRecyclerAdapter).setLocationData(list)
                }
            })
    }
}

fun checkSelectedLocationFromDB(fragment: MainFragment) {
    val locationList = MainFragmentArgs.fromBundle(fragment.requireArguments()).locationdatadb
    if (locationList != null) {
        if (locationList.list.isNotEmpty()) {
            fragment.viewModel.setLocationList(locationList.list as ArrayList<LocationInfo>)
            MainFragmentArgs.fromBundle(fragment.requireArguments()).locationdatadb?.list =
                emptyList()
        }
    }
}