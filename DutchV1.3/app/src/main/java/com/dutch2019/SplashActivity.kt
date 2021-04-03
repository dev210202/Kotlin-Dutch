package com.dutch2019

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import com.dutch2019.ui.main.MainActivity

class SplashActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val uiOptions = window.decorView.systemUiVisibility
        var newUiOptions = uiOptions
        val isImmersiveModeEnabled =
            uiOptions or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY == uiOptions
        newUiOptions = newUiOptions xor View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        newUiOptions = newUiOptions xor View.SYSTEM_UI_FLAG_FULLSCREEN
        newUiOptions = newUiOptions xor View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        window.decorView.systemUiVisibility = newUiOptions
        startLoading()
    }

    private fun startLoading() {
        val handler = Handler()
        handler.postDelayed({
            finish()
            val gotoMain = Intent(applicationContext, MainActivity::class.java)
            startActivity(gotoMain)
        }, 2000)
    }
}