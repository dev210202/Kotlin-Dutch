package com.dutch2019.extension

import android.content.Intent
import android.widget.Button
import android.widget.ImageButton
import androidx.databinding.BindingAdapter
import com.dutch2019.base.BaseViewModel
import com.dutch2019.ui.main.MainViewModel
import com.dutch2019.ui.search.SearchLocationActivity

@BindingAdapter(value = ["plusbuttonclick"])
fun plusButtonClick(imageButton: ImageButton, viewModel: BaseViewModel){
    imageButton.setOnClickListener {
        (viewModel as MainViewModel).addDynamicButtonData()
    }
}


//@BindingAdapter(value = ["minusbuttonclick"])
//fun minusButtonClick(button : Button, viewModel: BaseViewModel){
//    button.setOnClickListener {
//        (viewModel as MainViewModel).removeDynamicButtonData()
//    }
//}