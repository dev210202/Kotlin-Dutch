package com.dutch2019.util

import android.content.Context
import androidx.core.content.ContextCompat
import com.dutch2019.R

fun getDefaultTextColor(context: Context) = ContextCompat.getColor(context, R.color.gray3)

fun getChangedTextColor(context: Context) = ContextCompat.getColor(context, R.color.gray7)


fun getDisableTextColor(context: Context) = ContextCompat.getColor(context, R.color.gray2_30)
fun getActiveTextColor(context: Context) = ContextCompat.getColor(context, R.color.white)

fun getDisableBackground(context: Context) = ContextCompat.getDrawable(context, R.drawable.button_gray_round_30)
fun getActiveBackground(context: Context) = ContextCompat.getDrawable(context, R.drawable.button_primary_round)
