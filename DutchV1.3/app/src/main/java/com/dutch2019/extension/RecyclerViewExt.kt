package com.dutch2019.extension

import android.util.Log
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.dutch2019.adapter.ButtonRecyclerViewAdapter
import com.dutch2019.model.LocationInfo
import com.dutch2019.adapter.SearchRecyclerAdapter

@BindingAdapter(value = ["createbutton"])
fun createButton(recyclerView: RecyclerView, buttonData: LiveData<ArrayList<LocationInfo>>) {
    if (recyclerView.adapter == null) {
        var adapter = ButtonRecyclerViewAdapter()
        recyclerView.adapter = adapter
    }
    (recyclerView.adapter as ButtonRecyclerViewAdapter).setLocationData(buttonData.value!!)
    recyclerView.adapter?.notifyDataSetChanged()
}

@BindingAdapter(value = ["searchlocation"])
fun searchLocation(recyclerView: RecyclerView, locationData: LiveData<ArrayList<LocationInfo>>) {
    if (recyclerView.adapter == null) {
        var adapter = SearchRecyclerAdapter()
        recyclerView.adapter = adapter
    }
    (recyclerView.adapter as SearchRecyclerAdapter).setLocationDataList(locationData.value!!)
    recyclerView.adapter?.notifyDataSetChanged()
}