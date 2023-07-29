package com.dutch2019.util

import android.content.Context
import android.widget.Button
import com.dutch2019.model.LocationData

fun Any?.isNotNull() = this != null

fun LocationData.isSameData(compareData: LocationData) =
    this.lat == compareData.lat && this.lon == compareData.lon

fun LocationData.isSamePoint(compareLat: Double, compareLon: Double) =
    this.lat == compareLat && this.lon == compareLon
fun LocationData.isNotSamePoint(compareLat: Double, compareLon: Double) =
    this.lat != compareLat || this.lon != compareLon
fun Button.isNotSelected() = !this.isSelected
fun changeToDP(value: Int, context: Context) = value * context.resources.displayMetrics.density