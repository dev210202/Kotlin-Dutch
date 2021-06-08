package com.dutch2019.extension

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import com.dutch2019.R
import com.dutch2019.base.BaseViewModel
import com.dutch2019.model.LocationInfo
import com.dutch2019.model.LocationInfoList
import com.dutch2019.ui.main.MainFragmentDirections
import com.dutch2019.ui.main.MainViewModel
import com.dutch2019.ui.middle.MiddleLocationFragmentDirections
import com.dutch2019.ui.middle.MiddleLocationViewModel
import com.dutch2019.ui.nearfacillity.NearFacilityFragmentDirections
import com.dutch2019.ui.recent.RecentFragmentDirections
import com.dutch2019.ui.search.SearchLocationViewModel
import kotlinx.android.synthetic.main.fragment_search_location.view.*
import java.net.URI

@BindingAdapter(value = ["plusbuttonclick"])
fun plusButtonClick(imageButton: ImageButton, viewModel: BaseViewModel) {
    imageButton.setOnClickListener {view ->
        view.findNavController().navigate(
            MainFragmentDirections.actionMainFragmentToSearchLocationFragment()
        )
    }
}

@BindingAdapter(value = ["historybuttonclick"])
fun historyButtonClick(imageButton: ImageButton, viewModel: BaseViewModel) {
    imageButton.setOnClickListener { view ->
        view.findNavController()
            .navigate(MainFragmentDirections.actionMainFragmentToRecentFragment())
    }
}

@BindingAdapter(value = ["searchbuttonclick"])
fun searchButtonClick(imageButton: ImageButton, viewModel: BaseViewModel) {
    imageButton.setOnClickListener {
        val inputText = imageButton.rootView.inputedittext.text.toString()
        (viewModel as SearchLocationViewModel).searchLocationData(inputText)
    }
}

@BindingAdapter(value = ["ratiobuttonclick"])
fun ratioButtonClick(imageButton: ImageButton, viewModel: BaseViewModel) {
    imageButton.setOnClickListener { view ->
        var locationInfoList = LocationInfoList()
        (viewModel as MiddleLocationViewModel).getLocationList().forEach { locationInfo ->
            locationInfoList.add(locationInfo)
        }
        view.findNavController().navigate(
            MiddleLocationFragmentDirections.actionMiddleLocationFragmentToRatioFragment()
        )
    }
}
@BindingAdapter(value = ["resetbuttonclick"])
fun resetButtonClick(imageButton: ImageButton, viewModel: BaseViewModel) {
    imageButton.setOnClickListener {view ->
        var dialog = Dialog(imageButton.rootView.context)
        dialog.setContentView(R.layout.dialog_setting)
        dialog.show()
        var yesButton = dialog.findViewById<Button>(R.id.yes_button)
        var noButton = dialog.findViewById<Button>(R.id.no_button)

        yesButton.setOnClickListener {  _ ->
            dialog.dismiss()
            (viewModel as MiddleLocationViewModel).resetRatioPoint()
            val navController = view.findNavController()
            navController.popBackStack()
        }
        noButton.setOnClickListener {
            dialog.dismiss()
        }
    }
}

@BindingAdapter(value = ["detailinfo"])
fun detailInfo(imageButton: ImageButton, locationInfo: LocationInfo) {
    imageButton.setOnClickListener {view ->
        view.findNavController().navigate(NearFacilityFragmentDirections.actionNearFacilityFragmentToDetailInfoFragment(locationInfo))
    }
}

@BindingAdapter(value = ["searchinternet"])
fun searchInternet(imageButton: ImageButton, name: String) {
    imageButton.setOnClickListener {
        val intent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse("https://m.search.naver.com/search.naver?where=nexearch&sm=top_hty&fbm=1&ie=utf8&query=$name")
        )
        imageButton.context.startActivity(intent)
    }
}

@BindingAdapter(value = ["deleterecentlocation"])
fun deleteRecentLocation(imageButton: ImageButton, viewModel: BaseViewModel) {
    imageButton.setOnClickListener { view ->
        view.findNavController()
            .navigate(RecentFragmentDirections.actionRecentFragmentToDeleteRecentFragment())
    }
}
