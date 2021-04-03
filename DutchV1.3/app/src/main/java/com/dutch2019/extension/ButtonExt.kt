package com.dutch2019.extension

import android.content.Intent
import android.widget.Button
import androidx.databinding.BindingAdapter
import com.dutch2019.model.LocationData


@BindingAdapter(value =["setlocationbuttonclick"])
fun setLocationButtonCLick(button : Button, locationData: LocationData){
    val CHOOSE_COMPLETE = 20
    val intent = Intent()

}


//@BindingAdapter(value = ["minusbuttonclick"])
//fun minusButtonClick(button : Button, viewModel: BaseViewModel){
//    button.setOnClickListener {
//        (viewModel as MainViewModel).removeDynamicButtonData()
//    }
//}