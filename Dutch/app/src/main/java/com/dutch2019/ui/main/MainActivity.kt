package com.dutch2019.ui.main

import android.os.Bundle
import android.util.Log
import android.view.ViewTreeObserver
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.dutch2019.BuildConfig
import com.dutch2019.R
import com.dutch2019.base.LifeCycleActivity
import com.dutch2019.databinding.ActivityMainBinding
import com.dutch2019.util.getMessageByErrorTypeClassify
import com.dutch2019.util.toast
import com.skt.Tmap.TMapTapi
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : LifeCycleActivity<ActivityMainBinding>(R.layout.activity_main) {

    private val vm: MainViewModel by viewModels()
    private val tMapTapi: TMapTapi by lazy { TMapTapi(this) }
    override fun onCreate(savedInstanceState: Bundle?) {

        installSplashScreen()

        super.onCreate(savedInstanceState)

        vm.loadSearchData()
        setTMapAPIAuth()

        vm.isCompleteLoadDatabase.observe(this) {
            binding.root.viewTreeObserver.dispatchOnPreDraw()
        }

        binding.root.viewTreeObserver.addOnPreDrawListener(createPreDrawListener())

    }

    private fun createPreDrawListener(): ViewTreeObserver.OnPreDrawListener {
        return object : ViewTreeObserver.OnPreDrawListener {
            override fun onPreDraw(): Boolean {
                return if (vm.isCompleteLoadDatabase()) {
                    binding.root.viewTreeObserver.removeOnPreDrawListener(this)
                    setTheme(R.style.Theme_Dutch)
                    true
                } else {
                    binding.root.viewTreeObserver.removeOnPreDrawListener(this)
                    setTheme(R.style.Theme_Dutch)
                    false
                }
            }
        }
    }

    private fun setTMapAPIAuth() {
        tMapTapi.apply {
            setOnAuthenticationListener(AuthCallback())
            setSKTMapAuthentication(BuildConfig.T_MAP_API)
        }
    }
    inner class AuthCallback : TMapTapi.OnAuthenticationListenerCallback {

        override fun SKTMapApikeySucceed() {
            vm.setCompleteLoadDatabase()
        }

        override fun SKTMapApikeyFailed(errorMessage: String?) {
            vm.setCompleteLoadDatabase()
            runOnUiThread {
                toast(getMessageByErrorTypeClassify(errorMessage))
            }
        }
    }

}