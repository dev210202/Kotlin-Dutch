package com.dutch2019.ui.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.Observer
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.dutch2019.R
import com.dutch2019.adapter.ButtonRecyclerViewAdapter
import com.dutch2019.adapter.SearchRecyclerAdapter
import com.dutch2019.base.BaseFragment
import com.dutch2019.databinding.FragmentMainBinding
import com.dutch2019.model.LocationInfo
import com.dutch2019.ui.locationcheck.LocationCheckFragmentArgs
import com.skt.Tmap.TMapTapi


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
                    (binding.recyclerview?.adapter as ButtonRecyclerViewAdapter).setLocationData(
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