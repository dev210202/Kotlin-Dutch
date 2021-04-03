package com.dutch2019.extension

import android.util.Log
import android.widget.ImageButton
import androidx.databinding.BindingAdapter
import com.dutch2019.base.BaseViewModel
import com.dutch2019.ui.main.MainViewModel
import com.dutch2019.ui.search.SearchLocationViewModel
import kotlinx.android.synthetic.main.activity_search_location.view.*
import kotlinx.android.synthetic.main.fragment_search_location.view.*

@BindingAdapter(value = ["plusbuttonclick"])
fun plusButtonClick(imageButton: ImageButton, viewModel: BaseViewModel){
    imageButton.setOnClickListener {
        (viewModel as MainViewModel).addDynamicButtonData()
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