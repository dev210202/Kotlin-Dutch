package com.dutch2019.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.location_list_item.view.*
import com.dutch2019.model.LocationData
import com.dutch2019.R

class SearchRecyclerAdapter(private var dataList: LiveData<ArrayList<LocationData>>) :
    RecyclerView.Adapter<SearchRecyclerAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(parent)

    override fun getItemCount(): Int = dataList.value!!.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        dataList.value!![position].let { item ->
            with(holder) {
                locationNameTextView.text = item.locationName
                locationAddressTextView.text = item.locationAddress
            }
        }
    }

    class ViewHolder constructor(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.location_list_item, parent, false)
    ) {
        val locationNameTextView: TextView = itemView.locationnametextview
        val locationAddressTextView: TextView = itemView.locationaddresstextview
    }

}