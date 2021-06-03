package com.dutch2019.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel : ViewModel() {
    private val _toastVaule = MutableLiveData<String>()
    val toastValue : MutableLiveData<String> get() = _toastVaule


    var compositeDisposable = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

    fun getCompsiteDisposable(): CompositeDisposable? {
        return compositeDisposable
    }
}