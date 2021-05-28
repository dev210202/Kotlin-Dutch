package com.dutch2019.ui.recent

import android.os.Bundle
import androidx.lifecycle.Observer
import com.dutch2019.R
import com.dutch2019.adapter.RecentRecyclerAdapter
import com.dutch2019.base.BaseFragment
import com.dutch2019.databinding.FragmentRecentBinding
import com.dutch2019.model.LocationDataDB

class RecentFragment : BaseFragment<FragmentRecentBinding, RecentViewModel>(
    R.layout.fragment_recent,
    RecentViewModel::class.java
) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.initList()
        viewModel.initDB(requireActivity().application)
        viewModel.getRecentLocationDB()
        viewModel.locationList.observe(this, Observer { list ->
            if (binding.recyclerview.adapter != null) {
                (binding.recyclerview.adapter as RecentRecyclerAdapter).setLocationDataDB(list)
                (binding.recyclerview.adapter as RecentRecyclerAdapter).notifyDataSetChanged()
            }
        })
    }
}