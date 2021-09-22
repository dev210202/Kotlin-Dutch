package com.dutch2019.base

import android.os.Bundle
import android.os.PersistableBundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import jkey20.dutch.BR
import java.lang.reflect.ParameterizedType

abstract class BaseActivity<B : ViewDataBinding, VM : BaseViewModel>(
    layoutRes: Int,
    viewModelClass: Class<VM>
) :
    AppCompatActivity(layoutRes) {

    val binding by lazy {
        DataBindingUtil.bind<B>(
            (window.decorView.findViewById(android.R.id.content) as ViewGroup).getChildAt(0)
        )!!
    }

    open val viewModel by lazy {
        ViewModelProvider(this).get(viewModelClass)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.setVariable(BR.vm, viewModel)
    }

    fun getViewModelClass(): Class<VM> {
        val type = ((javaClass.genericSuperclass as ParameterizedType?)
            ?.actualTypeArguments
            ?.get(1) as Class<VM>).kotlin
        return type as Class<VM>
    }

}