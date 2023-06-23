package com.dutch2019.ui.main

import android.os.Bundle
import android.util.Log
import android.view.ViewTreeObserver
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.dutch2019.BuildConfig
import com.dutch2019.R
import com.dutch2019.base.BaseActivity
import com.dutch2019.databinding.ActivityMainBinding
import com.dutch2019.util.getMessageByErrorTypeClassify
import com.dutch2019.util.toast
import com.skt.Tmap.TMapTapi
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private val vm: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {

        installSplashScreen()

        super.onCreate(savedInstanceState)

        TMapTapi(this).apply {
            setOnAuthenticationListener(object : TMapTapi.OnAuthenticationListenerCallback {

                override fun SKTMapApikeySucceed() = vm.setConfirmedSktMapApikey()

                override fun SKTMapApikeyFailed(errorMessage: String?) {
                    vm.setConfirmedSktMapApikey()
                    toast(getMessageByErrorTypeClassify(errorMessage))
                }
            })
            setSKTMapAuthentication("${BuildConfig.T_MAP_API}")
        }

        vm.isConfirmedSktMapApikey.observe(this) {
            binding.root.viewTreeObserver.dispatchOnPreDraw()
        }

        binding.root.viewTreeObserver.addOnPreDrawListener(object :
            ViewTreeObserver.OnPreDrawListener {
            override fun onPreDraw(): Boolean {
                return if (vm.isConfirmedSktMapApikey()) {
                    binding.root.viewTreeObserver.removeOnPreDrawListener(this)
                    true
                } else {
                    false
                }
            }
        })
    }
}