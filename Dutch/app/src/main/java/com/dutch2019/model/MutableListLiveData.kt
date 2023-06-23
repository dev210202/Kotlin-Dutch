package com.dutch2019.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class MutableListLiveData<T>: MutableLiveData<List<T>>() {
    private val temp = mutableListOf<T>()

    init {
        value = temp
    }

    fun add(item: T) {
        temp.add(item)
        value = temp.toMutableList()
    }

    fun addAll(items: List<T>) {
        temp.addAll(items)
        value = temp
    }

    fun remove(item: T) {
        temp.remove(item)
        value = temp
    }

    fun clear() {
        temp.clear()
        value = temp
    }
}