package com.dutch2019.ui.main

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import com.dutch2019.R
import com.dutch2019.adapter.ButtonRecyclerAdapter
import com.dutch2019.base.BaseFragment
import com.dutch2019.databinding.FragmentMainBinding


class MainFragment : BaseFragment<FragmentMainBinding, MainViewModel>(
    R.layout.fragment_main,
    MainViewModel::class.java
) {
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        val navController = findNavController()
//
//        navController.currentBackStackEntry?.savedStateHandle?.getLiveData<LocationInfo>("locationInfo")
//            ?.observe(viewLifecycleOwner) {
//                data ->
//                Log.i("DATA NAV", data.name)
//                viewModel.addDynamicButtonData(data)
//            }

//        var locationInfo = MainFragmentArgs.fromBundle(requireArguments()).let {
//                data -> viewModel.addDynamicButtonData(data.locationInfo)
//        }

        viewModel.dynamicButtonData.observe(
            viewLifecycleOwner,
            Observer { dynamicButtonList ->
                dynamicButtonList.forEach {
                    Log.i("list", it.name)
                }
                if (binding.recyclerview?.adapter != null) {
                    (binding.recyclerview?.adapter as ButtonRecyclerAdapter).setLocationData(
                        dynamicButtonList
                    )
                }
            })
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("DESTORY","DESTROY")
    }
}