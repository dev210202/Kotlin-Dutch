package com.dutch2019.Adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.location_list_item.view.*
import com.dutch2019.Data.LocationData
import com.dutch2019.R

class NearRecyclerAdapter(var dataList: LiveData<ArrayList<LocationData>>) :
    RecyclerView.Adapter<NearRecyclerAdapter.ViewHolder>() {

    class ViewHolder constructor(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.location_list_item, parent, false)
    ) {
        val locationNameTextView = itemView.locationnametextview
        val locationAddressTextView = itemView.locationaddresstextview
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(parent)

    override fun getItemCount(): Int = dataList.value!!.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        dataList.value!!.get(position).let { item ->
            with(holder) {
                locationNameTextView.text = item.locationName
                locationAddressTextView.text = item.locationAddress
                Log.e("locationsetting", item.locationName + "\n" + item.locationAddress)
            }
        }
    }
}