package com.dutch2019.ui.nearfacillity

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dutch2019.R
import com.dutch2019.base.BaseFragment
import com.dutch2019.databinding.FragmentNearFacilityBinding
import com.dutch2019.ui.middle.MiddleLocationFragmentArgs


class NearFacilityFragment : BaseFragment<FragmentNearFacilityBinding, NearFacilityViewModel>(
    R.layout.fragment_near_facility,
    NearFacilityViewModel::class.java
) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var locationInfo = NearFacilityFragmentArgs.fromBundle(requireArguments()).let { data ->
            viewModel.setLocaitonPoint(data.locationpoint.latitude, data.locationpoint.longitude)
        }
    }


}