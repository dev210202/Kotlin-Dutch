package com.dutch2019.ui.main

import android.os.Bundle
import androidx.lifecycle.Observer
import com.dutch2019.R
import com.dutch2019.adapter.DynamicButtonRecyclerAdapter
import com.dutch2019.base.BaseFragment
import com.dutch2019.databinding.FragmentMainBinding


class MainFragment : BaseFragment<FragmentMainBinding, MainViewModel>(
    R.layout.fragment_main,
    MainViewModel::class.java
) {
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        checkSelectedLocationFromDB(this)

        viewModel.initDB(requireActivity().application)
        viewModel.dynamicButtonData.observe(
            viewLifecycleOwner,
            Observer { dynamicButtonList ->
                if (binding.recyclerview.adapter != null) {
                    (binding.recyclerview.adapter as DynamicButtonRecyclerAdapter).setLocationData(
                        dynamicButtonList
                    )
                }
            })
    }
}

fun checkSelectedLocationFromDB(fragment: MainFragment){
    val locationList = MainFragmentArgs.fromBundle(fragment.requireArguments()).locationdatadb
    if (locationList != null) {
        if (locationList.list.isNotEmpty()) {
            fragment.viewModel.clearDynamicButtonData()
            locationList.list.forEach { data ->
                fragment.viewModel.addDynamicButtonData(data)
            }
            MainFragmentArgs.fromBundle(fragment.requireArguments()).locationdatadb?.list = emptyList()
        }
    }
}