package com.dutch2019.util

import android.content.Context
import androidx.core.content.ContextCompat
import com.dutch2019.R

fun getDefaultTextColor(context: Context) = ContextCompat.getColor(context, R.color.gray3)

fun getChangedTextColor(context: Context) = ContextCompat.getColor(context, R.color.gray7)