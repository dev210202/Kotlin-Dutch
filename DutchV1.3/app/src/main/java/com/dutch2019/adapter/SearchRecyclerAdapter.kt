package com.dutch2019.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.dutch2019.databinding.LocationListItemBinding
import com.dutch2019.model.LocationInfo
import com.dutch2019.ui.search.SearchLocationFragmentDirections

class SearchRecyclerAdapter() :
    RecyclerView.Adapter<SearchRecyclerAdapter.LocationDataViewHolder>() {

    private var locationDataList = ArrayList<LocationInfo>()

    fun setLocationDataList(list: ArrayList<LocationInfo>) {
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
        holder.layout.setOnClickListener { view : View ->
            view.findNavController().navigate(
                SearchLocationFragmentDirections.actionSearchLocationFragmentToLocationCheckFragment(
                    locationDataList[position]
                )
            )
        }
    }

    class LocationDataViewHolder(private val binding: LocationListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        var layout = binding.linearLayout
        fun bind(locationInfo: LocationInfo) {
            binding.locationinfo = locationInfo

        }
    }

}