package com.dutch2019.extension

import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.dutch2019.adapter.*
import com.dutch2019.factory.RecyclerAdapterFactory
import com.dutch2019.model.LocationDataDB
import com.dutch2019.model.LocationInfo

@BindingAdapter(value = ["createbutton"])
fun createButton(recyclerView: RecyclerView, buttonData: LiveData<ArrayList<LocationInfo>>) {
    setRecyclerView(recyclerView, "createButton")
    (recyclerView.adapter as ButtonRecyclerAdapter).setLocationData(buttonData.value!!)
    recyclerView.adapter?.notifyDataSetChanged()
}

@BindingAdapter(value = ["searchlocation"])
fun searchLocation(recyclerView: RecyclerView, locationData: LiveData<ArrayList<LocationInfo>>) {
    setRecyclerView(recyclerView, "searchLocation")
    (recyclerView.adapter as SearchRecyclerAdapter).setLocationDataList(locationData.value!!)
    recyclerView.adapter?.notifyDataSetChanged()
}

@BindingAdapter(value = ["nearfacility"])
fun nearFacility(recyclerView: RecyclerView, locationData: LiveData<ArrayList<LocationInfo>>) {
    setRecyclerView(recyclerView, "nearFacility")
    (recyclerView.adapter as NearRecyclerAdapter).setLocationDataList(locationData.value!!)
    recyclerView.adapter?.notifyDataSetChanged()
}

@BindingAdapter(value = ["recent"])
fun recent(recyclerView: RecyclerView, locationData: LiveData<List<LocationDataDB>>) {
    setRecyclerView(recyclerView, "recent")
    (recyclerView.adapter as RecentRecyclerAdapter).setLocationDataDB(locationData.value!!)
    recyclerView.adapter?.notifyDataSetChanged()
}

@BindingAdapter(value = ["deleterecent"])
fun deleteRecent(recyclerView: RecyclerView, locationData: LiveData<List<LocationDataDB>>) {
    setRecyclerView(recyclerView, "deleteRecent")
    (recyclerView.adapter as DeleteRecentRecyclerAdapter).setLocationDataDB(locationData.value!!)
    recyclerView.adapter?.notifyDataSetChanged()
}

fun setRecyclerView(recyclerView: RecyclerView, recyclerName: String) {
    if (recyclerView.adapter == null) {
        recyclerView.adapter = RecyclerAdapterFactory().createAdapter(recyclerName)
    }
}
