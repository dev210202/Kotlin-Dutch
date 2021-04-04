package com.dutch2019.extension

import android.util.Log
import android.widget.Button
import androidx.databinding.BindingAdapter
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.dutch2019.base.BaseViewModel
import com.dutch2019.model.LocationInfo
import com.dutch2019.ui.locationcheck.LocationCheckFragmentDirections
import com.dutch2019.ui.main.MainViewModel


@BindingAdapter(value =["setlocationbuttonclick"])
fun setLocationButtonCLick(button : Button, viewModel: BaseViewModel){
    button.setOnClickListener {
        view ->

     //   (viewModel as MainViewModel).addDynamicButtonData(viewModel.checkLocationInfo.value!!)
        (viewModel as MainViewModel).replaceDynamicButtonData(viewModel.checkLocationInfo.value!!.id, viewModel.checkLocationInfo.value!!)
        (viewModel as MainViewModel).dynamicButtonData.value!!.forEach {
            Log.i("BUTTONEXT DATA", it.name)
        }
        val navController = view.findNavController()
        navController.popBackStack()
        navController.popBackStack()
//        view.findNavController().popBackStack()
//        view.findNavController().navigate(LocationCheckFragmentDirections.actionLocationCheckFragmentToMainFragment(locationInfo))

    }
}


//@BindingAdapter(value = ["minusbuttonclick"])
//fun minusButtonClick(button : Button, viewModel: BaseViewModel){
//    button.setOnClickListener {
//        (viewModel as MainViewModel).removeDynamicButtonData()
//    }
//}