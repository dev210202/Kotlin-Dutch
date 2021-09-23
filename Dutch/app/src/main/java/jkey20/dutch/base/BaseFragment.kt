package com.dutch2019.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import jkey20.dutch.BR
import jkey20.dutch.R
import java.lang.reflect.ParameterizedType

abstract class BaseFragment<B : ViewDataBinding>(
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
        return view
    }


}