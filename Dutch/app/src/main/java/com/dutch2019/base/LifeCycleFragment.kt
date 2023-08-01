package com.dutch2019.base

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class LifeCycleFragment<B : ViewDataBinding>(
    layoutRes: Int
) : Fragment(layoutRes) {

    protected lateinit var binding: B
        private set

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        binding = DataBindingUtil.bind(view!!)!!
        binding.lifecycleOwner = viewLifecycleOwner

        Log.i("onCreateView","!")

        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("onCreate","!")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i("onViewCreated","!")
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        Log.i("onViewStateRestored","!")
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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.i("onSaveInstanceState","!")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.i("onDestroyView","!")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("onDestroy","!")
    }
}