package com.dutch2019.util

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.NonNull
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.findNavController
import com.dutch2019.R
import com.dutch2019.util.marker.Marker

enum class ButtonState {
    ACTIVE, DISABLE, DEFAULT;

    fun changeButton(button: Button) = when (this) {
        ACTIVE -> {
            button.isEnabled = true
            button.isSelected = true
            button.setTextColor(Color.CTA_ACTIVE.getColor(button.context))
        }
        DISABLE -> {
            button.isEnabled = false
            button.isSelected = false
            button.setTextColor(Color.CTA_DISABLED.getColor(button.context))
        }
        DEFAULT -> {
            button.isSelected = false
            button.setTextColor(Color.CTA_DEFAULT.getColor(button.context))
        }
    }
}

enum class Color {
    CTA_DEFAULT, CTA_DISABLED, CTA_ACTIVE, TEXT_DEFAULT, TEXT_WHITE, TEXT_ACTIVE, TEXT_PRIMARY, TEXT_RATIO, MARKER_DEFAULT, BACKGROUND_SELECTED, BACKGROUND_DEFAULT
    ;

    fun getColor(context: Context) = when (this) {
        CTA_DEFAULT -> {
            ContextCompat.getColor(context, R.color.gray3)
        }
        CTA_DISABLED -> {
            ContextCompat.getColor(context, R.color.gray2_30)
        }
        CTA_ACTIVE, TEXT_WHITE -> {
            ContextCompat.getColor(context, R.color.white)
        }
        TEXT_DEFAULT -> {
            ContextCompat.getColor(context, R.color.gray2)
        }
        TEXT_ACTIVE, BACKGROUND_DEFAULT, MARKER_DEFAULT -> {
            ContextCompat.getColor(context, R.color.gray7)
        }
        TEXT_PRIMARY -> {
            ContextCompat.getColor(context, R.color.primary)
        }
        TEXT_RATIO -> {
            ContextCompat.getColor(context, R.color.skyblue)
        }
        BACKGROUND_SELECTED -> {
            ContextCompat.getColor(context, R.color.gray6)
        }
    }
}

lateinit var loadingDialog: ProgressDialog
fun getBoldTextFont(context: Context) = ResourcesCompat.getFont(context, R.font.pretendard_bold)
fun showLoadingDialog(activity: Activity) {
    loadingDialog = ProgressDialog(activity)
    loadingDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER)
    loadingDialog.setMessage("로딩중입니다..")
    loadingDialog.show()
}

fun dismissLoadingDialog() {
    loadingDialog.dismiss()
}


fun setDefaultLocationItem(context: Context, textview: TextView, layout: FrameLayout) {
    textview.text = "위치를 입력해주세요"
    textview.setTextColor(Color.CTA_DEFAULT.getColor(context))
    Color.CTA_DEFAULT.getColor(context)
    layout.background = Marker.DEFAULT.getMark(context)
}


fun onClickLeftArrowButton(view: View){
    view.setOnClickListener {
        view.findNavController().popBackStack()
    }
}