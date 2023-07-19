package com.dutch2019.ui.main

import android.os.Bundle
import android.view.ViewTreeObserver
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.dutch2019.BuildConfig
import com.dutch2019.R
import com.dutch2019.base.BaseActivity
import com.dutch2019.databinding.ActivityMainBinding
import com.dutch2019.ui.middle.MiddleViewModel
import com.dutch2019.util.getMessageByErrorTypeClassify
import com.dutch2019.util.toast
import com.skt.Tmap.TMapTapi
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private val vm: MainViewModel by viewModels()
    private val middleVM : MiddleViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {

        installSplashScreen()

        super.onCreate(savedInstanceState)

        // TODO : 최초 실행시 DB 로드
        vm.loadSearchData()
        setTMapAPIAuth()

        vm.isConfirmedSktMapApikey.observe(this) {
            binding.root.viewTreeObserver.dispatchOnPreDraw()
        }

        binding.root.viewTreeObserver.addOnPreDrawListener(createPreDrawListener())

    }

    private fun createPreDrawListener(): ViewTreeObserver.OnPreDrawListener {
        return object : ViewTreeObserver.OnPreDrawListener {
            override fun onPreDraw(): Boolean {
                return if (vm.isConfirmedSktMapApikey()) {
                    binding.root.viewTreeObserver.removeOnPreDrawListener(this)
                    setTheme(R.style.Theme_Dutch)
                    true
                } else {
                    false
                }
            }
        }
    }

    private fun setTMapAPIAuth() {
        TMapTapi(this).apply {
            setOnAuthenticationListener(object : TMapTapi.OnAuthenticationListenerCallback {

                override fun SKTMapApikeySucceed() = vm.setConfirmedSktMapApikey()

                override fun SKTMapApikeyFailed(errorMessage: String?) {
                    vm.setConfirmedSktMapApikey()
                    runOnUiThread {
                        toast(getMessageByErrorTypeClassify(errorMessage))
                    }
                }
            })
            setSKTMapAuthentication(BuildConfig.T_MAP_API)
        }
    }

}