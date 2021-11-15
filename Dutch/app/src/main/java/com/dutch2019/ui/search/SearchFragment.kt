package com.dutch2019.ui.search

import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.dutch2019.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import com.dutch2019.R
import com.dutch2019.adapter.SearchRecyclerAdapter
import com.dutch2019.databinding.FragmentSearchBinding

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>(
    R.layout.fragment_search
) {

    private val searchViewModel: SearchViewModel by viewModels()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.recyclerviewSearch.adapter = SearchRecyclerAdapter()

        searchViewModel.locationList.observe(viewLifecycleOwner, Observer { list ->
            if(list == null){
                Toast.makeText(requireContext(), "검색된 값이 없습니다.", Toast.LENGTH_LONG).show()
            }
            else {
                (binding.recyclerviewSearch.adapter as SearchRecyclerAdapter).setLocationDataList(list)
            }
        })

        binding.layoutSearch.setOnClickListener {
            searchViewModel.search(binding.edittextSearch.text.toString())
        }
        binding.searchSearchbutton.setOnClickListener {
            searchViewModel.search(binding.edittextSearch.text.toString())
        }
        binding.layoutRecent.setOnClickListener {
            requireView().findNavController().navigate(
                SearchFragmentDirections.actionSearchFragmentToRecentFragment()
            )
        }
        binding.searchRecentbutton.setOnClickListener{
            requireView().findNavController().navigate(
                SearchFragmentDirections.actionSearchFragmentToRecentFragment()
            )
        }
    }
}