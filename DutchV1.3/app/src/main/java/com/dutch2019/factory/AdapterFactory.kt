package com.dutch2019.factory

import androidx.recyclerview.widget.RecyclerView

abstract class AdapterFactory {
    abstract fun createAdapter(name : String) : Any
}