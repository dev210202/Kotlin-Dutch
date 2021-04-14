package com.dutch2019.ui.deleterecent

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.dutch2019.R
import com.dutch2019.adapter.DeleteRecnetRecyclerAdapter
import com.dutch2019.adapter.RecentRecyclerAdapter
import com.dutch2019.base.BaseFragment
import com.dutch2019.databinding.FragmentDeleteRecentBinding
import com.dutch2019.ui.recent.RecentViewModel

class DeleteRecentFragment : BaseFragment<FragmentDeleteRecentBinding, RecentViewModel>(
    R.layout.fragment_delete_recent,
    RecentViewModel::class.java
) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.initList()
        viewModel.initDB(requireActivity().application)
        viewModel.getRecentLocationDB()
        viewModel.locationList.observe(this, Observer { list ->
            if (binding.recyclerview.adapter != null) {
                (binding.recyclerview.adapter as DeleteRecnetRecyclerAdapter).setLocationDataDB(list)
            }
        })
    }
}