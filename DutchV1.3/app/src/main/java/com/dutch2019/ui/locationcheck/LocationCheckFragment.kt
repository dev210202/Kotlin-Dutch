package com.dutch2019.ui.locationcheck

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.dutch2019.R
import com.dutch2019.base.BaseFragment
import com.dutch2019.databinding.FragmentLocationCheckBinding
import com.dutch2019.ui.main.MainViewModel


class LocationCheckFragment : BaseFragment<FragmentLocationCheckBinding, MainViewModel>(
    R.layout.fragment_location_check,
    MainViewModel::class.java
) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         var locationInfo = LocationCheckFragmentArgs.fromBundle(requireArguments()).let {
                data -> viewModel.setCheckLocationInfo(data.locationinfo)
        }
    }
}
