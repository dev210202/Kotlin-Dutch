package com.dutch2019.extension

import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dutch2019.adapter.ButtonRecyclerViewAdapter
import com.dutch2019.base.BaseViewModel
import com.dutch2019.model.LocationInfo
import com.dutch2019.ui.main.MainViewModel

@BindingAdapter(value = ["createbutton"])
fun createButton(recyclerView: RecyclerView, buttonData: LiveData<ArrayList<LocationInfo>>) {
    if (recyclerView.adapter == null) {
        var adapter = ButtonRecyclerViewAdapter()
        recyclerView.adapter = adapter
    }
    (recyclerView.adapter as ButtonRecyclerViewAdapter).setLocationData(buttonData.value!!)
    recyclerView.adapter?.notifyDataSetChanged()

}