package com.dutch2019.ui.search

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
import com.dutch2019.databinding.FragmentSearchLocationBinding
import com.dutch2019.ui.locationcheck.LocationCheckFragmentArgs
import com.skt.Tmap.TMapTapi

class SearchLocationFragment : BaseFragment<FragmentSearchLocationBinding, SearchLocationViewModel>(
    R.layout.fragment_search_location,
    SearchLocationViewModel::class.java
) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        viewModel.init()

        var locationInfo = SearchLocationFragmentArgs.fromBundle(requireArguments()).let { data ->
            viewModel.locationPosition = data.locationInfo.id
        }

        viewModel.locationList.observe(this, Observer { list ->
            if (binding.recyclerview.adapter != null) {
                (binding.recyclerview.adapter as SearchRecyclerAdapter).setLocationDataList(list)
            }
        })
    }
}