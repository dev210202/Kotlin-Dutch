package com.dutch2019.util

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log


fun checkNetWorkStatus(context: Context) : String{
    val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val netWork = manager.activeNetworkInfo

    // todo : network 유무에 따라 처리할것
    if(netWork != null){
        if(netWork.type == ConnectivityManager.TYPE_WIFI){
            Log.i("WIFI", "!!")
            return "WIFI"
        }
        if(netWork.type == ConnectivityManager.TYPE_MOBILE){
            Log.i("MOBILE", "!!")
            return "MOBILE"
        }
    }
    return "NONE"
}
