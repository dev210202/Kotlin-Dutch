package com.dutch2019.ui.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.dutch2019.R
import com.dutch2019.adapter.SearchRecyclerAdapter
import com.dutch2019.base.BaseFragment
import com.dutch2019.databinding.FragmentSearchLocationBinding
import com.skt.Tmap.TMapTapi

class SearchLocationFragment : BaseFragment<FragmentSearchLocationBinding, SearchLocationViewModel>(
    R.layout.fragment_search_location,
    SearchLocationViewModel::class.java
) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val tmapAPI = TMapTapi(activity)
        tmapAPI.setSKTMapAuthentication("l7xx75e02f3eccaa4f56b3f269cb4a9f2b43")
        viewModel.locationList.observe(this, Observer { list ->
            if (binding.recyclerview.adapter != null) {
                (binding.recyclerview.adapter as SearchRecyclerAdapter).setLocationDataList(list)
            }
        })
    }
}