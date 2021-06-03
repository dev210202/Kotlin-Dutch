package com.dutch2019.ui.detailinfo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dutch2019.R
import com.dutch2019.adapter.DeleteRecentRecyclerAdapter
import com.dutch2019.base.BaseFragment
import com.dutch2019.databinding.FragmentDetailInfoBinding


class DetailInfoFragment : BaseFragment<FragmentDetailInfoBinding, DetailInfoViewModel>(
    R.layout.fragment_detail_info,
    DetailInfoViewModel::class.java
) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}