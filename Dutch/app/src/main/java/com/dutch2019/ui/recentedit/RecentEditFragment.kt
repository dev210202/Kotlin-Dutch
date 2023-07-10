package com.dutch2019.ui.recentedit

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.dutch2019.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import com.dutch2019.R
import com.dutch2019.adapter.RecentEditRecyclerAdapter
import com.dutch2019.databinding.FragmentRecentEditBinding
import com.dutch2019.ui.search.SearchViewModel

@AndroidEntryPoint
class RecentEditFragment : BaseFragment<FragmentRecentEditBinding>(
    R.layout.fragment_recent_edit
) {
    private val vm: SearchViewModel by viewModels()
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.rvRecentEdit.apply {
           adapter = RecentEditRecyclerAdapter()
        }

        binding.ibLeftArrow.setOnClickListener {
            findNavController().popBackStack()
        }

        /*
         TODO
          1: 편집시 DB에서 삭제처리
          2: checkbox 선택시 하단 버튼 개수 변경처리
         */
    }
}