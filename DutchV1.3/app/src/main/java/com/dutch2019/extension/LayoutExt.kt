package com.dutch2019.extension

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.BitmapFactory
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import com.dutch2019.R
import com.dutch2019.base.BaseViewModel
import com.dutch2019.model.LocationInfo
import com.dutch2019.ui.main.MainFragmentDirections
import com.dutch2019.ui.main.MainViewModel
import com.dutch2019.ui.middle.MiddleLocationViewModel
import com.skt.Tmap.TMapMarkerItem
import com.skt.Tmap.TMapPoint
import com.skt.Tmap.TMapView


@BindingAdapter(value = ["plusbuttonlayoutclick"])
fun plusButtonLayoutClick(layout: ConstraintLayout, viewModel: BaseViewModel) {
    layout.setOnClickListener { view ->
        view.findNavController().navigate(
            MainFragmentDirections.actionMainFragmentToSearchLocationFragment()
        )
    }
}

@BindingAdapter(value = ["resetbuttonlayoutclick"])
fun resetButtonLayoutClick(layout: ConstraintLayout, viewModel: BaseViewModel) {
    layout.setOnClickListener { view ->
        var dialog = Dialog(layout.context)
        dialog.setContentView(R.layout.dialog_setting)
        var yesButton = dialog.findViewById<Button>(R.id.yes_button)
        var noButton = dialog.findViewById<Button>(R.id.yes_button)

        yesButton.setOnClickListener {
            dialog.dismiss()
            (viewModel as MiddleLocationViewModel).resetRatioPoint()
        }
        noButton.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()

    }
}