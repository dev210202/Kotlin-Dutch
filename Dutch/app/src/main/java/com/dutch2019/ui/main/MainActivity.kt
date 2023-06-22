package com.dutch2019.ui.main

import android.os.Bundle
import android.util.Log
import android.view.ViewTreeObserver
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.dutch2019.BuildConfig
import com.dutch2019.R
import com.dutch2019.base.BaseActivity
import com.dutch2019.databinding.ActivityMainBinding
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

                override fun SKTMapApikeySucceed() = vm.setSKTMapApikeySuccess()

                override fun SKTMapApikeyFailed(errorMessage: String?) {
                    vm.setSKTMapApikeyFail()
                    when (errorMessage) {
                        // 인증메시지별 처리
                        // 에러 메시지를 넘겨 vm에 넘기게
                    }
                }
            })
            setSKTMapAuthentication("${BuildConfig.T_MAP_API}")
        }

        vm.sktMapApikeyAuth.observe(this) { isSuccess ->
            binding.root.viewTreeObserver.dispatchOnPreDraw()
            if (isSuccess == false) {
                // 에러 메시지 표시
            }
        }

        binding.root.viewTreeObserver.addOnPreDrawListener(object :
            ViewTreeObserver.OnPreDrawListener {
            override fun onPreDraw(): Boolean {
                return if (vm.isSKTMapApikeySuccess()) {
                    binding.root.viewTreeObserver.removeOnPreDrawListener(this)
                    true
                } else {
                    false
                }
            }
        })
    }
}