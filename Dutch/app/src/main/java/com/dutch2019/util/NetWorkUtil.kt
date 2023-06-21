package com.dutch2019.util

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log

enum class NetWorkStatus{
    WIFI, MOBILE, NONE
}
fun checkNetWorkStatus(context: Context) : NetWorkStatus{
    val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val netWork = manager.activeNetworkInfo

    if(netWork != null){
        if(netWork.type == ConnectivityManager.TYPE_WIFI){
            return NetWorkStatus.WIFI
        }
        if(netWork.type == ConnectivityManager.TYPE_MOBILE){
            return NetWorkStatus.MOBILE
        }
    }
    return NetWorkStatus.NONE
}
