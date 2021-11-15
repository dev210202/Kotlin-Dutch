package com.dutch2019.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {
    private val _toastVaule = MutableLiveData<String>()
    val toastValue : MutableLiveData<String> get() = _toastVaule

}