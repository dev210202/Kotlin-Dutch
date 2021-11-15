package com.dutch2019.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.kakao.sdk.common.util.Utility
import com.skt.Tmap.TMapTapi
import dagger.hilt.android.AndroidEntryPoint
import com.dutch2019.BuildConfig
import com.dutch2019.R
import com.dutch2019.util.checkNetWorkStatus

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val tmapApi = TMapTapi(this)
        tmapApi.setSKTMapAuthentication("${BuildConfig.T_MAP_API}")

        var keyHash = Utility.getKeyHash(this)
        Log.i("keyHash", keyHash)

        Log.i("NETWORK CHECK", checkNetWorkStatus(this))
    }
}