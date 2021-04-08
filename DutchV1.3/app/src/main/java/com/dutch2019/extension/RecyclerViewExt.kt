package com.dutch2019.extension

import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.dutch2019.adapter.ButtonRecyclerAdapter
import com.dutch2019.model.LocationInfo
import com.dutch2019.adapter.SearchRecyclerAdapter
import com.dutch2019.adapter.NearRecyclerAdapter

@BindingAdapter(value = ["createbutton"])
fun createButton(recyclerView: RecyclerView, buttonData: LiveData<ArrayList<LocationInfo>>) {
    if (recyclerView.adapter == null) {
        var adapter = ButtonRecyclerAdapter()
        recyclerView.adapter = adapter
    }
    (recyclerView.adapter as ButtonRecyclerAdapter).setLocationData(buttonData.value!!)
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
@BindingAdapter(value = ["nearfacility"])
fun nearFacility(recyclerView: RecyclerView, locationData: LiveData<ArrayList<LocationInfo>>){
    if (recyclerView.adapter == null) {
        var adapter = NearRecyclerAdapter()
        recyclerView.adapter = adapter
    }
    (recyclerView.adapter as NearRecyclerAdapter).setLocationDataList(locationData.value!!)
    recyclerView.adapter?.notifyDataSetChanged()
}