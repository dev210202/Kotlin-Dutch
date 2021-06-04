package com.dutch2019.ui.ratio

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.dutch2019.R
import com.dutch2019.base.BaseFragment
import com.dutch2019.databinding.FragmentRatioBinding
import com.dutch2019.ui.middle.MiddleLocationViewModel


class RatioFragment : BaseFragment<FragmentRatioBinding, MiddleLocationViewModel>(
    R.layout.fragment_ratio,
    MiddleLocationViewModel::class.java
) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        RatioFragmentArgs.fromBundle(requireArguments()).let { data ->
//            viewModel.locationInfoList = data.locationinfolist
//        }
//
        viewModel.ratio.observe(this, Observer { ratio ->
            binding.seekbarvalueTextview.text = ratio
        })

    }
}