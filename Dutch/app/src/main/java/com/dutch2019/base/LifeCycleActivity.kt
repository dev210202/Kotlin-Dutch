package com.dutch2019.base

import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class LifeCycleActivity<B : ViewDataBinding>(
    layoutRes: Int
) : AppCompatActivity(layoutRes) {

    val binding by lazy {
        DataBindingUtil.bind<B>(
            (window.decorView.findViewById(android.R.id.content) as ViewGroup).getChildAt(0)
        )!!
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("onCreate","!")
    }
    override fun onStart() {
        super.onStart()
        Log.i("onStart","!")
    }

    override fun onResume() {
        super.onResume()
        Log.i("onStart","!")
    }

    override fun onPause() {
        super.onPause()
        Log.i("onPause","!")
    }

    override fun onStop() {
        super.onStop()
        Log.i("onStop","!")
    }
    override fun onDestroy() {
        super.onDestroy()
        Log.i("onDestroyView","!")
    }

    override fun onRestart() {
        super.onRestart()
        Log.i("onRestart","!")
    }
}