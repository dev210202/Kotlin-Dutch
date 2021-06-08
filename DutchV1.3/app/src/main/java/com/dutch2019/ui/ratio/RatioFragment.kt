package com.dutch2019.ui.ratio

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.Observer
import com.dutch2019.R
import com.dutch2019.base.BaseFragment
import com.dutch2019.databinding.FragmentRatioBinding
import com.dutch2019.ui.middle.MiddleLocationViewModel


class RatioFragment : BaseFragment<FragmentRatioBinding, MiddleLocationViewModel>(
    R.layout.fragment_ratio,
    MiddleLocationViewModel::class.java
) {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.ratio.observe(viewLifecycleOwner, Observer { ratio ->
            binding.seekbarvalueTextview.text = ratio
        })

    }
}