package com.dutch2019.util

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.dutch2019.R
import com.dutch2019.util.marker.getDefaultMarkerBackground

object ButtonState {
    const val ACTIVE = "Active"
    const val DISABLE = "Disable"
    const val DEFAULT = "Default"
}
lateinit var loadingDialog : ProgressDialog
fun getDefaultCTAButtonTextColor(context: Context) = ContextCompat.getColor(context, R.color.cta_button_text_default)
fun getDisabledCTAButtonTextColor(context: Context) = ContextCompat.getColor(context, R.color.cta_button_text_disabled)
fun getActiveCTAButtonTextColor(context: Context) = ContextCompat.getColor(context, R.color.cta_button_text_active)

fun getDefaultTextColor(context: Context) = ContextCompat.getColor(context, R.color.item_text_default)
fun getActiveTextColor(context: Context) = ContextCompat.getColor(context, R.color.gray7)
fun getPrimaryTextColor(context: Context) = ContextCompat.getColor(context, R.color.primary)
fun getDisableBackground(context: Context) = ContextCompat.getDrawable(context, R.drawable.button_gray_round_30)
fun getActiveBackground(context: Context) = ContextCompat.getDrawable(context, R.drawable.button_primary_round)
fun getSelectedBackgroundColor(context: Context) = ContextCompat.getColor(context, R.color.gray6)
fun getDefaultBackgroundColor(context: Context) = ContextCompat.getColor(context, R.color.gray7)

fun getBoldTextFont(context : Context) = ResourcesCompat.getFont(context, R.font.pretendard_bold)
fun showLoadingDialog(activity: Activity){
    loadingDialog = ProgressDialog(activity)
    loadingDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER)
    loadingDialog.setMessage("로딩중입니다..")
    loadingDialog.show()
}

fun dismissLoadingDialog(){
    loadingDialog.dismiss()
}

fun setButtonState(button : Button, state: String){
    when(state){
        ButtonState.ACTIVE -> {
            button.isEnabled = true
            button.isSelected = true
            button.setTextColor(getActiveCTAButtonTextColor(button.context))
        }
        ButtonState.DEFAULT -> {
            button.isSelected = false
            button.setTextColor(getDefaultCTAButtonTextColor(button.context))
        }
        ButtonState.DISABLE -> {
            button.isEnabled = false
            button.isSelected = false
            button.setTextColor(getDisabledCTAButtonTextColor(button.context))
        }
    }
}

 fun setDefaultLocationItem(context : Context, textview: TextView, layout: FrameLayout) {
    textview.text = "위치를 입력해주세요"
    textview.setTextColor(getDefaultCTAButtonTextColor(context))
    layout.background = getDefaultMarkerBackground(context)
}
