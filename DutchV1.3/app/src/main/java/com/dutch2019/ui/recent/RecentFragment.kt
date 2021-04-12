package com.dutch2019.ui.recent

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.dutch2019.R
import com.dutch2019.base.BaseFragment
import com.dutch2019.databinding.FragmentRecentBinding
import com.dutch2019.ui.nearfacillity.NearFacilityFragmentArgs

class RecentFragment : BaseFragment<FragmentRecentBinding, RecentViewModel>(
    R.layout.fragment_recent,
    RecentViewModel::class.java
) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.initDB(requireActivity().application)
        viewModel.getRecentLocationDB()
        viewModel.locationList.observe(this, Observer { list ->
            list.forEach { listdata ->
                Log.i("LOCATION DATA DB ID", ": " +listdata.id)
                listdata.list.forEach {
                    info ->
                    Log.i("LOCATION INFO", "" + info.id + " " + info.name + " " + info.address + " " + info.latitude + " " + info.longitude)
                }
            }
        })
    }
}