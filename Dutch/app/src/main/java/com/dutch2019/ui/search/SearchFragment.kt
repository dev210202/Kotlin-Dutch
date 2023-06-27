package com.dutch2019.ui.search

import android.os.Bundle
import android.transition.Visibility
import android.view.KeyEvent
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.dutch2019.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import com.dutch2019.R
import com.dutch2019.adapter.EmptyDataObserver
import com.dutch2019.adapter.RecentRecyclerAdapter
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

        val recentLocationList =
            SearchFragmentArgs.fromBundle(requireArguments()).locationdbdatalist
        vm.setRecentLocationList(recentLocationList.convertLocationDBDataListToData())
        if (recentLocationList.value.isEmpty()) {
            binding.btnEdit.visibility = View.INVISIBLE
            binding.tvEmpty.visibility = View.VISIBLE
        }

        val emptyDataObserver = EmptyDataObserver(binding.recyclerviewSearch, binding.tvEmpty)
        val searchAdapter = SearchRecyclerAdapter(onRightArrowButtonClicked = { locationData ->
            findNavController().navigate(
                SearchFragmentDirections.actionSearchFragmentToLocationCheckFragment(
                    locationData = locationData
                )
            )
        }).apply {
            registerAdapterDataObserver(emptyDataObserver)
        }
        val recentAdapter = RecentRecyclerAdapter().apply {
            setLocationDataList(vm.getRecentLocationList())
        }.apply {
            registerAdapterDataObserver(emptyDataObserver)
        }
        binding.recyclerviewSearch.apply {
            adapter = recentAdapter
        }



        vm.tMapPOIItemList.observe(viewLifecycleOwner, Observer { list ->
            if (list.isNotEmpty()) {
                binding.tvInfo.text = "검색결과"
                binding.recyclerviewSearch.adapter = searchAdapter
                (binding.recyclerviewSearch.adapter as SearchRecyclerAdapter).setTMapPOIItemList(list)
            }
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

        binding.ibClose.setOnClickListener {
            binding.tvInfo.text = "최근검색"
            binding.etSearch.text = null
            binding.recyclerviewSearch.adapter = recentAdapter
        }
    }
}