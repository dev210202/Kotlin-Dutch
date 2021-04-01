package com.dutch2019.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.location_list_item.view.*
import com.dutch2019.model.LocationData
import com.dutch2019.R
import com.dutch2019.databinding.LocationListItemBinding
import com.dutch2019.ui.middle.MiddleLocationActivity

class SearchRecyclerAdapter() :
    RecyclerView.Adapter<SearchRecyclerAdapter.LocationDataViewHolder>() {

    private var locationDataList = ArrayList<LocationData>()

    fun setLocationDataList(list: ArrayList<LocationData>) {
        locationDataList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationDataViewHolder {
        val binding =
            LocationListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LocationDataViewHolder(binding)
    }

    override fun getItemCount(): Int = locationDataList.size


    override fun onBindViewHolder(holder: LocationDataViewHolder, position: Int) {
        holder.bind(locationDataList[position])
        holder.rightArrowButton.setOnClickListener {
            val intent = Intent(holder.itemView.context, MiddleLocationActivity::class.java)
            holder.itemView.context.startActivity(intent)
        }
    }

    class LocationDataViewHolder(private val binding: LocationListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        var rightArrowButton = binding.rightArrowButton
        fun bind(locationData : LocationData){
            binding.locationdata = locationData
        }
    }

}