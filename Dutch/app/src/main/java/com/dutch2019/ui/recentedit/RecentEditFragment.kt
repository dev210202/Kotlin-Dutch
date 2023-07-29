package com.dutch2019.ui.recentedit

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.dutch2019.R
import com.dutch2019.adapter.RecentEditRecyclerAdapter
import com.dutch2019.base.BaseFragment
import com.dutch2019.databinding.FragmentRecentEditBinding
import com.dutch2019.ui.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecentEditFragment : BaseFragment<FragmentRecentEditBinding>(
    R.layout.fragment_recent_edit
) {
    private val vm: MainViewModel by activityViewModels()
    private val recentEditAdapter by lazy {
        RecentEditRecyclerAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvRecentEdit.apply {
            recentEditAdapter.setLocationDataList(vm.getLocationDBList())
            adapter = recentEditAdapter
        }

        binding.ibLeftArrow.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnSelectAll.setOnClickListener {
            recentEditAdapter.selectAllCheckBox(isSelectState = { isSelect ->
                if (isSelect) {
                    binding.btnSelectAll.text = "전체 해제"
                } else {
                    binding.btnSelectAll.text = "전체 선택"
                }
            })
        }

        binding.btnDelete.setOnClickListener {
            recentEditAdapter.getCheckList().apply {
                vm.deleteCheckedList(this)
                vm.changeSearchLocationList(this)
            }
            findNavController().navigate(RecentEditFragmentDirections.actionRecentEditFragmentToSearchFragment())
        }

    }
}