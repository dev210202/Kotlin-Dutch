package com.dutch2019.extension

import android.util.Log
import android.widget.Button
import androidx.databinding.BindingAdapter
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.dutch2019.base.BaseViewModel
import com.dutch2019.model.LocationInfo
import com.dutch2019.model.LocationInfoList
import com.dutch2019.ui.locationcheck.LocationCheckFragmentDirections
import com.dutch2019.ui.main.MainFragmentDirections
import com.dutch2019.ui.main.MainViewModel
import com.dutch2019.ui.middle.MiddleLocationFragment


@BindingAdapter(value = ["setlocationbuttonclick"])
fun setLocationButtonClick(button: Button, viewModel: BaseViewModel) {
    button.setOnClickListener { view ->
        (viewModel as MainViewModel).replaceDynamicButtonData(
            viewModel.checkLocationInfo.value!!.id,
            viewModel.checkLocationInfo.value!!
        )
        (viewModel as MainViewModel).dynamicButtonData.value!!.forEach {
            Log.i("BUTTONEXT id", "" + it.id)
            Log.i("BUTTONEXT name", it.name)
            Log.i("BUTTONEXT address", it.adress)
            Log.i("BUTTONEXT lat", "" + it.latitude)
            Log.i("BUTTONEXT lon", "" + it.longitude)
        }
        val navController = view.findNavController()
        navController.popBackStack()
        navController.popBackStack()
    }
}

@BindingAdapter(value = ["searchmiddlelocationbuttonclick"])
fun searchMiddleLocationButtonClick(button: Button, viewModel: BaseViewModel) {
    var viewModel = (viewModel as MainViewModel)

    val locationInfoList = LocationInfoList()
    viewModel.dynamicButtonData.value!!.forEach { data ->
        locationInfoList.add(data)
    }
    button.setOnClickListener { view ->
        view.findNavController().navigate(
            MainFragmentDirections.actionMainFragmentToMiddleLocationFragment(locationInfoList)
        )
    }
}