package com.dutch2019.ui.main

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import com.dutch2019.R
import com.dutch2019.adapter.ButtonRecyclerAdapter
import com.dutch2019.base.BaseFragment
import com.dutch2019.databinding.FragmentMainBinding
import com.dutch2019.repository.LocationRepository


class MainFragment : BaseFragment<FragmentMainBinding, MainViewModel>(
    R.layout.fragment_main,
    MainViewModel::class.java
) {
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.initDB(requireActivity().application)
        viewModel.dynamicButtonData.observe(
            viewLifecycleOwner,
            Observer { dynamicButtonList ->
                if (binding.recyclerview?.adapter != null) {
                    (binding.recyclerview?.adapter as ButtonRecyclerAdapter).setLocationData(
                        dynamicButtonList
                    )
                }
            })
    }
}