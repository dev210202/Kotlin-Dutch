package com.dutch2019.ui.search

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import com.dutch2019.R
import com.dutch2019.adapter.SearchRecyclerAdapter
import com.dutch2019.base.BaseFragment
import com.dutch2019.databinding.FragmentSearchLocationBinding

class SearchLocationFragment : BaseFragment<FragmentSearchLocationBinding, SearchLocationViewModel>(
    R.layout.fragment_search_location,
    SearchLocationViewModel::class.java
) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.init()

        SearchLocationFragmentArgs.fromBundle(requireArguments()).let { data ->
            viewModel.locationPosition = data.locationInfo.id
        }
        viewModel.locationList.observe(this, Observer { list ->
            if (binding.recyclerview.adapter != null) {
                (binding.recyclerview.adapter as SearchRecyclerAdapter).setLocationDataList(list)
            }
        })
        viewModel.toastValue.observe(this, Observer { toastMessage ->
            Toast.makeText(activity, toastMessage, Toast.LENGTH_LONG)
        })
    }
}