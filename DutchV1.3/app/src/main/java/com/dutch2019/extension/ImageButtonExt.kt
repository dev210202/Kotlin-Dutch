package com.dutch2019.extension

import android.content.Intent
import android.net.Uri
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
import com.dutch2019.ui.recent.RecentFragmentDirections
import com.dutch2019.ui.search.SearchLocationViewModel
import kotlinx.android.synthetic.main.fragment_search_location.view.*
import java.net.URI

@BindingAdapter(value = ["plusbuttonclick"])
fun plusButtonClick(imageButton: ImageButton, viewModel: BaseViewModel) {
    imageButton.setOnClickListener {
        (viewModel as MainViewModel).addDummyLocationData()
    }
}

@BindingAdapter(value = ["historybuttonclick"])
fun historyButtonClick(imageButton: ImageButton, viewModel: BaseViewModel) {
    var viewModel = (viewModel as MainViewModel)
    imageButton.setOnClickListener { view ->
        view.findNavController()
            .navigate(MainFragmentDirections.actionMainFragmentToRecentFragment())
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

@BindingAdapter(value = ["searchinternet"])
fun searchInternet(imageButton: ImageButton, name: String) {
    var intent = Intent(
        Intent.ACTION_VIEW,
        Uri.parse("https://search.naver.com/search.naver?where=nexearch&sm=top_hty&fbm=1&ie=utf8&query=$name")
    )
    imageButton.context.startActivity(intent)
}

@BindingAdapter(value = ["deleterecentlocation"])
fun deleteRecentLocation(imageButton: ImageButton, viewModel : BaseViewModel){
    imageButton.setOnClickListener {view ->
       view.findNavController().navigate(RecentFragmentDirections.actionRecentFragmentToDeleteRecentFragment())
    }
}