package com.dutch2019.ui.main

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.activityViewModels
import com.dutch2019.BuildConfig
import com.dutch2019.R
import com.dutch2019.base.BaseActivity
import com.dutch2019.base.LifeCycleActivity
import com.dutch2019.databinding.ActivityMainBinding
import com.dutch2019.ui.RecentSearchViewModel
import com.dutch2019.util.getMessageByErrorTypeClassify
import com.dutch2019.util.toast
import com.skt.Tmap.TMapTapi
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private val vm: RecentSearchViewModel by viewModels()
    private val tMapTapi: TMapTapi by lazy { TMapTapi(this) }
    private var isTMapAPIAuthSuccess = false
    override fun onCreate(savedInstanceState: Bundle?) {

        val splashScreen = installSplashScreen()

        super.onCreate(savedInstanceState)

        splashScreen.setKeepOnScreenCondition {
            if (isTMapAPIAuthSuccess) {
                setTheme(R.style.Theme_Dutch)
                false
            } else {
                true
            }
        }

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        actionBar?.hide()

        vm.loadSearchData()
        setTMapAPIAuth()
    }


    private fun setTMapAPIAuth() {
        tMapTapi.apply {
            setOnAuthenticationListener(AuthCallback())
            setSKTMapAuthentication(BuildConfig.T_MAP_API)
        }
    }

    inner class AuthCallback : TMapTapi.OnAuthenticationListenerCallback {

        override fun SKTMapApikeySucceed() {
            isTMapAPIAuthSuccess = true
        }

        override fun SKTMapApikeyFailed(errorMessage: String?) {
            throw Throwable("TMap API 인증 오류")
        }
    }

}