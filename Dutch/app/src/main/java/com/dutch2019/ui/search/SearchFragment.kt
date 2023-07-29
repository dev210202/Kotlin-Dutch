package com.dutch2019.ui.search

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.dutch2019.R
import com.dutch2019.adapter.EmptyDataObserver
import com.dutch2019.adapter.SearchRecyclerAdapter
import com.dutch2019.base.BaseFragment
import com.dutch2019.databinding.FragmentSearchBinding
import com.dutch2019.model.LocationData
import com.dutch2019.ui.main.MainViewModel
import com.dutch2019.util.convertTMapPOIItemToLocationData
import com.dutch2019.util.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>(
    R.layout.fragment_search
) {

    private val vm: SearchViewModel by activityViewModels()
    private val mainVm: MainViewModel by activityViewModels()
    private val emptyDataObserver by lazy {
        EmptyDataObserver(
            binding.recyclerviewSearch, binding.tvEmpty
        )
    }
    private val searchAdapter by lazy {
        SearchRecyclerAdapter(onRightArrowButtonClicked = { locationData ->
            findNavController().navigate(
                SearchFragmentDirections.actionSearchFragmentToLocationCheckFragment(
                    locationData = locationData
                )
            )
        }).apply {
            registerAdapterDataObserver(emptyDataObserver)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainVm.getLocationDBList().apply {
            if (this.isEmpty()) {
                setEmptyViewVisible()
            }
            searchAdapter.setLocationItemList(this)
        }

        binding.recyclerviewSearch.apply {
            adapter = searchAdapter
        }


        vm.tMapPOIItemList.observe(viewLifecycleOwner) { list ->
            if (list.isNotEmpty()) {
                mutableListOf<LocationData>().apply {
                    list.forEach { listItem ->
                        this.add(convertTMapPOIItemToLocationData(listItem))
                    }
                }.apply {
                    searchAdapter.setLocationItemList(this)
                }
                binding.tvInfo.text = "검색결과"
                binding.tvEmpty.visibility = View.INVISIBLE
                binding.recyclerviewSearch.adapter = searchAdapter

            }
        }

        vm.inputValue.observe(viewLifecycleOwner) { input ->
            vm.search(input, showToast = { toastMessage ->
                activity!!.runOnUiThread {
                    context!!.toast(toastMessage)
                }
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
            setInitView()
        }

        binding.btnEdit.setOnClickListener {
            findNavController().navigate(
                SearchFragmentDirections.actionSearchFragmentToRecentEditFragment()
            )
        }
    }

    private fun setEmptyViewVisible() {
        binding.btnEdit.visibility = View.INVISIBLE
        binding.tvEmpty.visibility = View.VISIBLE
    }

    private fun setInitView() {
        binding.tvInfo.text = "최근검색"
        binding.etSearch.text = null
        searchAdapter.setLocationItemList(mainVm.getLocationDBList())
    }
}