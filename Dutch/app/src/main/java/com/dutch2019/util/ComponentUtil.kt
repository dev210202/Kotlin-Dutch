package com.dutch2019.util

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import androidx.core.content.ContextCompat
import com.dutch2019.R

lateinit var loadingDialog : ProgressDialog
fun getDefaultTextColor(context: Context) = ContextCompat.getColor(context, R.color.gray3)

fun getChangedTextColor(context: Context) = ContextCompat.getColor(context, R.color.gray7)


fun getDisableTextColor(context: Context) = ContextCompat.getColor(context, R.color.gray2_30)
fun getActiveTextColor(context: Context) = ContextCompat.getColor(context, R.color.white)

fun getDisableBackground(context: Context) = ContextCompat.getDrawable(context, R.drawable.button_gray_round_30)
fun getActiveBackground(context: Context) = ContextCompat.getDrawable(context, R.drawable.button_primary_round)

fun showLoadingDialog(activity: Activity){
    loadingDialog = ProgressDialog(activity)
    loadingDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER)
    loadingDialog.setMessage("로딩중입니다..")
    loadingDialog.show()
}

fun dismissLoadingDialog(){
    loadingDialog.dismiss()
}