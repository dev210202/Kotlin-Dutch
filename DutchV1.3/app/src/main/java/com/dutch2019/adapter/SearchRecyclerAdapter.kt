package com.dutch2019.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.dutch2019.model.LocationData
import com.dutch2019.databinding.LocationListItemBinding
import com.dutch2019.ui.locationcheck.LocationCheckFragment
import com.dutch2019.ui.search.SearchLocationFragmentDirections

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
        fun bind(locationData: LocationData) {
            binding.locationdata = locationData

        }
    }

}