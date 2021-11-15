package com.dutch2019.ui.recentedit

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.dutch2019.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import com.dutch2019.R
import com.dutch2019.adapter.RecentEditRecyclerAdapter
import com.dutch2019.databinding.FragmentRecentEditBinding

@AndroidEntryPoint
class RecentEditFragment : BaseFragment<FragmentRecentEditBinding>(
    R.layout.fragment_recent_edit
) {
    private val recentEditViewModel: RecentEditViewModel by viewModels()
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        recentEditViewModel.getRecentDB()
        binding.recyclerviewRecentedit.adapter = RecentEditRecyclerAdapter()
        binding.recenteditallCheckbox.setOnClickListener {
            (binding.recyclerviewRecentedit.adapter as RecentEditRecyclerAdapter).selectAllCheckBox()
            (binding.recyclerviewRecentedit.adapter as RecentEditRecyclerAdapter).notifyDataSetChanged()
        }
        binding.buttonRecentdelete.setOnClickListener {
            recentEditViewModel.deleteRecentDB((binding.recyclerviewRecentedit.adapter as RecentEditRecyclerAdapter).getDeleteList())
            binding.recenteditallCheckbox.isChecked = false
        }
        binding.buttonRecentcomplete.setOnClickListener {
            requireView().findNavController().popBackStack()
        }
        recentEditViewModel.recentLocationList.observe(viewLifecycleOwner, Observer { list ->
            (binding.recyclerviewRecentedit.adapter as RecentEditRecyclerAdapter).setLocationDataList(list)
        })
    }
}