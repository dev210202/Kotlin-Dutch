package jkey20.dutch.ui.search

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.dutch2019.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import jkey20.dutch.R
import jkey20.dutch.adapter.SearchRecyclerAdapter
import jkey20.dutch.databinding.FragmentSearchBinding

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>(
    R.layout.fragment_search
) {

    private val searchViewModel: SearchViewModel by viewModels()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.recyclerviewSearch.adapter = SearchRecyclerAdapter()

        searchViewModel.locationList.observe(viewLifecycleOwner, Observer { list ->
            (binding.recyclerviewSearch.adapter as SearchRecyclerAdapter).setLocationDataList(list)
            list.forEach {
                Log.i("value", it.poiAddress)
            }
        })

        binding.searchSearchbutton.setOnClickListener {
            searchViewModel.search(binding.edittextSearch.text.toString())
        }
    }
}