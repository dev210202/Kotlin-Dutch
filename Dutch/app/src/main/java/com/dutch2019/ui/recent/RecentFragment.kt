package com.dutch2019.ui.recent

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.dutch2019.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import com.dutch2019.R
import com.dutch2019.adapter.RecentRecyclerAdapter
import com.dutch2019.databinding.FragmentRecentBinding

@AndroidEntryPoint
class RecentFragment : BaseFragment<FragmentRecentBinding>(
    R.layout.fragment_recent
) {
    private val recentViewModel : RecentViewModel by viewModels()
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        recentViewModel.getRecentDB(requireActivity().application)
        binding.recyclerviewRecent.adapter = RecentRecyclerAdapter()
        binding.buttonRecentedit.setOnClickListener {
            requireView().findNavController().navigate(
                RecentFragmentDirections.actionRecentFragmentToRecentEditFragment()
            )
        }
        recentViewModel.recentLocationList.observe(viewLifecycleOwner, Observer { list ->
            (binding.recyclerviewRecent.adapter as RecentRecyclerAdapter).setLocationDataList(list)
        })
    }
}