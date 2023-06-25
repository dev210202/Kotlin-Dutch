package com.dutch2019.ui.search

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.findNavController
import com.dutch2019.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import com.dutch2019.R
import com.dutch2019.adapter.SearchRecyclerAdapter
import com.dutch2019.databinding.FragmentSearchBinding
import com.dutch2019.util.toast

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>(
    R.layout.fragment_search
) {

    private val vm: SearchViewModel by viewModels()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.recyclerviewSearch.adapter = SearchRecyclerAdapter(
            onRightArrowButtonClicked = { locationData ->
                findNavController().navigate(
                    SearchFragmentDirections.actionSearchFragmentToLocationCheckFragment(
                        locationData = locationData
                    )
                )
            }
        )

        vm.tMapPOIItemList.observe(viewLifecycleOwner, Observer { list ->
            (binding.recyclerviewSearch.adapter as SearchRecyclerAdapter).setTMapPOIItemList(list)
        })

        vm.inputValue.observe(this) { input ->
            vm.search(input, errorToast = { toastMessage ->
                context!!.toast(toastMessage)
            })
        }
        binding.ibSearch.setOnClickListener {
            vm.setInputValue(binding.etSearch.text.toString())
        }

        binding.etSearch.setOnKeyListener { view, keyCode, p2 ->
            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                vm.setInputValue(binding.etSearch.text.toString())
            }
            false
        }
    }
}