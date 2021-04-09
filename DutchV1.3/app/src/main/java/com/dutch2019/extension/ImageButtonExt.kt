package com.dutch2019.extension

import android.util.Log
import android.widget.ImageButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import com.dutch2019.base.BaseViewModel
import com.dutch2019.model.LocationInfo
import com.dutch2019.model.LocationInfoList
import com.dutch2019.ui.main.MainFragmentDirections
import com.dutch2019.ui.main.MainViewModel
import com.dutch2019.ui.search.SearchLocationViewModel
import kotlinx.android.synthetic.main.fragment_search_location.view.*

@BindingAdapter(value = ["plusbuttonclick"])
fun plusButtonClick(imageButton: ImageButton, viewModel: BaseViewModel) {
    imageButton.setOnClickListener {
        (viewModel as MainViewModel).addDummyLocationData()
    }
}

@BindingAdapter(value = ["historybuttonclick"])
fun historyButtonClikc(imageButton: ImageButton, viewModel: BaseViewModel){
    var viewModel = (viewModel as MainViewModel)
    imageButton.setOnClickListener {
        view ->
        var list = viewModel.getRecentDataInDB()
        var locationList = LocationInfoList()
        list.forEach {
            locationList.add(it)
        }
     view.findNavController().navigate(MainFragmentDirections.actionMainFragmentToRecentFragment(locationList))
    }
}

@BindingAdapter(value = ["searchbuttonclick"])
fun searchButtonClick(imageButton: ImageButton, viewModel: BaseViewModel) {

    imageButton.setOnClickListener {
        val inputText = imageButton.rootView.inputedittext.text.toString()
        Log.i("INPUTTEXT", inputText)
        (viewModel as SearchLocationViewModel).searchLocationData(inputText)


    }
}

