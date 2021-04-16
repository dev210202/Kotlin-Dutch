package com.dutch2019.extension

import android.util.Log
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.dutch2019.adapter.*
import com.dutch2019.model.LocationInfo
import com.dutch2019.model.LocationDataDB

@BindingAdapter(value = ["createbutton"])
fun createButton(recyclerView: RecyclerView, buttonData: LiveData<ArrayList<LocationInfo>>) {
    buttonData.value?.forEach {
        Log.i("recyclerviewext createbutton", it.name)
    }
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
fun nearFacility(recyclerView: RecyclerView, locationData: LiveData<ArrayList<LocationInfo>>) {
    if (recyclerView.adapter == null) {
        var adapter = NearRecyclerAdapter()
        recyclerView.adapter = adapter
    }
    (recyclerView.adapter as NearRecyclerAdapter).setLocationDataList(locationData.value!!)
    recyclerView.adapter?.notifyDataSetChanged()
}

@BindingAdapter(value = ["recent"])
fun recent(recyclerView: RecyclerView, locationData: LiveData<List<LocationDataDB>>) {
    if (recyclerView.adapter == null) {
        var adapter = RecentRecyclerAdapter()
        recyclerView.adapter = adapter
    }
    (recyclerView.adapter as RecentRecyclerAdapter).setLocationDataDB(locationData.value!!)
    recyclerView.adapter?.notifyDataSetChanged()
}

@BindingAdapter(value = ["deleterecent"])
fun deleteRecent(recyclerView: RecyclerView, locationData: LiveData<List<LocationDataDB>>) {
    if (recyclerView.adapter == null) {
        var adapter = DeleteRecentRecyclerAdapter()
        recyclerView.adapter = adapter
    }
    (recyclerView.adapter as DeleteRecentRecyclerAdapter).setLocationDataDB(locationData.value!!)
    recyclerView.adapter?.notifyDataSetChanged()
}