package com.dutch2019.util

import android.content.Context
import android.widget.Button
import com.dutch2019.model.LocationData

fun Any?.isNotNull() = this != null

fun Button.isNotSelected() = !this.isSelected
fun changeToDP(value: Int, context: Context) = value * context.resources.displayMetrics.density