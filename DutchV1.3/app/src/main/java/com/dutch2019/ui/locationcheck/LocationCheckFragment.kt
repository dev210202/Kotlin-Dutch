package com.dutch2019.ui.locationcheck

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dutch2019.R
import com.dutch2019.base.BaseFragment
import com.dutch2019.databinding.ActivityLocationCheckBinding
import com.dutch2019.databinding.FragmentLocationCheckBinding
import com.dutch2019.model.LocationData
import com.dutch2019.ui.search.SearchLocationFragmentDirections


class LocationCheckFragment : BaseFragment<FragmentLocationCheckBinding, LocationCheckViewModel>(
    R.layout.activity_location_check,
    LocationCheckViewModel::class.java
) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

         var locationData = LocationCheckFragmentArgs.fromBundle(requireArguments()).let {
             data -> viewModel.setData(data.locationData)
         }

//            locationData.locationData
//            (intent.getSerializableExtra("LocationData") as LocationData).let { data ->
//            viewModel.setData(locationData.locationData)
    }
}
