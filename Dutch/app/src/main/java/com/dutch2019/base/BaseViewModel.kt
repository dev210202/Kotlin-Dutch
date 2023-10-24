package com.dutch2019.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dutch2019.util.Event

abstract class BaseViewModel : ViewModel() {
    private val _toastValue = MutableLiveData<Event<String>>()
    val toastValue : LiveData<Event<String>> get() = _toastValue

    fun handleError(throwable: Throwable){
        val message = throwable.message ?: ""
        _toastValue.value = Event(message)
    }
}