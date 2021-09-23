package jkey20.dutch.search

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.dutch2019.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import jkey20.dutch.R
import jkey20.dutch.databinding.FragmentSearchBinding

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>(
    R.layout.fragment_search
) {

    private val viewModel: SearchViewModel by viewModels()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.locationList.observe(viewLifecycleOwner, Observer { list ->
            list.forEach {
                Log.i("value", it.name)
            }
        })

        binding.searchSearchbutton.setOnClickListener {
            viewModel.search(binding.edittextSearch.text.toString())
        }
    }
}