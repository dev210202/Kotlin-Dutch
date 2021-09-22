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
import jkey20.dutch.BR
import jkey20.dutch.R
import java.lang.reflect.ParameterizedType

abstract class BaseFragment<B : ViewDataBinding, VM : BaseViewModel>(
    layoutRes: Int,
    viewModelClass: Class<VM>
) : Fragment(layoutRes) {

    protected lateinit var binding: B
        private set
    open val viewModel by lazy {
        ViewModelProvider(
            requireActivity().viewModelStore,
            ViewModelProvider.NewInstanceFactory()
        ).get(viewModelClass)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        binding = DataBindingUtil.bind(view!!)!!
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.setVariable(BR.vm, viewModel)
    }

    fun getViewModelClass(): Class<VM> {
        val type = ((javaClass.genericSuperclass as ParameterizedType?)
            ?.actualTypeArguments
            ?.get(1) as Class<VM>).kotlin
        return type as Class<VM>
    }

}