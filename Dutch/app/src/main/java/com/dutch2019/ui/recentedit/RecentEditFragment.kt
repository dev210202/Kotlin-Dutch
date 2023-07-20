package com.dutch2019.ui.recentedit

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.dutch2019.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import com.dutch2019.R
import com.dutch2019.adapter.RecentEditRecyclerAdapter
import com.dutch2019.databinding.FragmentRecentEditBinding
import com.dutch2019.model.LocationDataList
import com.dutch2019.ui.search.SearchViewModel
import com.dutch2019.util.ButtonState
import com.dutch2019.util.setButtonState

@AndroidEntryPoint
class RecentEditFragment : BaseFragment<FragmentRecentEditBinding>(
    R.layout.fragment_recent_edit
) {
    private val vm: SearchViewModel by activityViewModels()
    private val recentEditAdapter by lazy {
        RecentEditRecyclerAdapter(onCheckBoxSelected = { locationdata, isChecked ->
            if (isChecked) {
                vm.addCheckedData(locationdata)
                setButtonState(binding.btnDelete, ButtonState.ACTIVE)
            } else {
                vm.removeCheckedData(locationdata)
                if (vm.isEmptyCheckboxList()) {
                    setButtonState(binding.btnDelete, ButtonState.DISABLE)
                }
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recentEditAdapter.setLocationDataList(vm.getSearchLocationList())
        Log.e("getSearchLocationList", vm.getSearchLocationList().toString())
        binding.rvRecentEdit.apply {
            adapter = recentEditAdapter
        }

        binding.ibLeftArrow.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnSelectAll.setOnClickListener { view ->
            recentEditAdapter.selectAllCheckBox(
                checkAllSelected = { isAllSelected ->
                    if (isAllSelected) {
                        (view as Button).text = "전체 해제"
                    } else {
                        (view as Button).text = "전체 선택"
                    }
                }
            )
        }

        binding.btnDelete.setOnClickListener {
            vm.deleteCheckedList(vm.getCheckboxList())
            vm.initSearchLocationList()
            findNavController().navigate(
                RecentEditFragmentDirections.actionRecentEditFragmentToSearchFragment(
                    locationdatalist =
                    LocationDataList()
                )
            )
        }
    }
}