package com.dutch2019.ui.search

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import androidx.fragment.app.activityViewModels
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
import com.skt.Tmap.poi_item.TMapPOIItem
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

        initRecyclerView().run {
            initAdapterItem()
        }

        vm.searchPOIItemList.observe(viewLifecycleOwner) { list ->
            if (list.isNotEmpty()) {
                convertLocationItemList(list).apply {
                    searchAdapter.setLocationItemList(this)
                }
                setSearchResultView()
            }
        }

        initButtonSearch()
        initEditTextSearch()
        initButtonClose()
        initButtonEdit()

    }

    private fun initButtonEdit() {
        binding.btnEdit.setOnClickListener {
            findNavController().navigate(
                SearchFragmentDirections.actionSearchFragmentToRecentEditFragment()
            )
        }
    }

    private fun initButtonClose() {
        binding.ibClose.setOnClickListener {
            setRecentSearchView()
        }
    }

    private fun initEditTextSearch() {
        binding.etSearch.setOnKeyListener { view, keyCode, p2 ->
            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                vm.search(binding.etSearch.text.toString(), showToast = { toastMessage ->
                    activity!!.runOnUiThread {
                        context!!.toast(toastMessage)
                    }
                })
                binding.btnEdit.visibility = INVISIBLE
            }
            false
        }
    }

    private fun initButtonSearch() {
        binding.ibSearch.setOnClickListener {
            vm.search(binding.etSearch.text.toString(), showToast = { toastMessage ->
                activity!!.runOnUiThread {
                    context!!.toast(toastMessage)
                }
            })
            binding.btnEdit.visibility = INVISIBLE
        }
    }

    private fun convertLocationItemList(list: List<TMapPOIItem>): List<LocationData> {
        return mutableListOf<LocationData>().apply {
            list.forEach { listItem ->
                Log.e("listItem", listItem.toString())
                this.add(convertTMapPOIItemToLocationData(listItem))
            }
        }
    }


    private fun initAdapterItem() {
        mainVm.getLocationDBList().apply {
            if (this.isEmpty()) {
                setEmptyViewVisible()
            }
            searchAdapter.setLocationItemList(this)
        }
    }

    private fun initRecyclerView() {
        binding.recyclerviewSearch.apply {
            adapter = searchAdapter
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        vm.clearSearchItemList()
    }

    private fun setEmptyViewVisible() {
        binding.btnEdit.visibility = INVISIBLE
        binding.tvEmpty.visibility = VISIBLE
    }

    private fun setRecentSearchView() {
        binding.tvInfo.text = "최근검색"
        binding.etSearch.text = null
        binding.btnEdit.visibility = VISIBLE
        searchAdapter.setLocationItemList(mainVm.getLocationDBList())
    }

    private fun setSearchResultView() {
        binding.tvInfo.text = "검색결과"
        binding.tvEmpty.visibility = INVISIBLE
        binding.btnEdit.visibility = INVISIBLE
        binding.recyclerviewSearch.adapter = searchAdapter
    }


}