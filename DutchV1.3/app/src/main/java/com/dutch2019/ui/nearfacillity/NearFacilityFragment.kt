package com.dutch2019.ui.nearfacillity

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.dutch2019.R
import com.dutch2019.adapter.NearRecyclerAdapter
import com.dutch2019.adapter.SearchRecyclerAdapter
import com.dutch2019.base.BaseFragment
import com.dutch2019.databinding.FragmentNearFacilityBinding
import com.dutch2019.ui.middle.MiddleLocationFragmentArgs


class NearFacilityFragment : BaseFragment<FragmentNearFacilityBinding, NearFacilityViewModel>(
    R.layout.fragment_near_facility,
    NearFacilityViewModel::class.java
) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.initList()

        var locationInfo = NearFacilityFragmentArgs.fromBundle(requireArguments()).let { data ->
            viewModel.setLocaitonPoint(data.locationpoint.latitude, data.locationpoint.longitude)
        }

        viewModel.locationList.observe(this, Observer { list ->
            if (binding.recyclerview.adapter != null) {
                (binding.recyclerview.adapter as NearRecyclerAdapter).setLocationDataList(list)
            }
        })
        viewModel.detailInfo.observe(this, Observer { info ->
            Log.i("DETAIL INFO data", info)
        })

    }
}