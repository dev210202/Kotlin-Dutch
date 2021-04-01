package com.dutch2019.extension

import android.util.Log
import android.widget.ImageButton
import androidx.databinding.BindingAdapter
import com.dutch2019.base.BaseViewModel
import com.dutch2019.ui.search.SearchLocationViewModel
import kotlinx.android.synthetic.main.activity_search_location.view.*

@BindingAdapter(value = ["searchbuttonclick"])
fun searchButtonClick(imageButton: ImageButton, viewModel: BaseViewModel) {

    imageButton.setOnClickListener {
        val inputText = imageButton.rootView.inputedittext.text.toString()
        Log.i("INPUTTEXT", inputText)
        (viewModel as SearchLocationViewModel).searchLocationData(inputText)


    }
}