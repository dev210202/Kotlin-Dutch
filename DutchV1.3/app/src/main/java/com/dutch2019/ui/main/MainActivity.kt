package com.dutch2019.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dutch2019.R
import com.skt.Tmap.TMapTapi

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val tmapAPI = TMapTapi(this)
        tmapAPI.setSKTMapAuthentication("l7xx75e02f3eccaa4f56b3f269cb4a9f2b43")
    }
}