package com.dutch2019.ui.detailinfo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.dutch2019.R
import com.dutch2019.adapter.DeleteRecentRecyclerAdapter
import com.dutch2019.base.BaseFragment
import com.dutch2019.databinding.FragmentDetailInfoBinding


class DetailInfoFragment : BaseFragment<FragmentDetailInfoBinding, DetailInfoViewModel>(
    R.layout.fragment_detail_info,
    DetailInfoViewModel::class.java
) {
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        DetailInfoFragmentArgs.fromBundle(requireArguments()).let { data ->
            viewModel.getDetailInfo(data.locationinfo.id)
            viewModel.point = data.locationinfo
        }
        viewModel.detailInfo.observe(viewLifecycleOwner, Observer { data ->

            binding.textviewDetailName.text = data.poiDetailInfo.name
            binding.textviewDetailAddress.text = "주소: " + data.poiDetailInfo.address
            binding.textviewDetailTel.text = "전화번호: " + data.poiDetailInfo.tel
            binding.textviewDetailDescription.text = "소개: " + data.poiDetailInfo.desc
            binding.textviewDetailAdditional.text = "부가정보: " + viewModel.filtAdditionalInfo(data.poiDetailInfo.additionalInfo)
        })
    }
}