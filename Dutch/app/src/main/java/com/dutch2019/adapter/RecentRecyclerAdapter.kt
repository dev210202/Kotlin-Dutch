package com.dutch2019.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.dutch2019.databinding.ItemRecentBinding
import com.dutch2019.model.LocationDBData
import com.dutch2019.model.LocationData
import com.dutch2019.model.LocationDataList
import com.dutch2019.ui.recent.RecentFragmentDirections

class RecentRecyclerAdapter :
    RecyclerView.Adapter<RecentRecyclerAdapter.RecentDataViewHolder>() {

    private var locationDataList = listOf<LocationDBData>()

    fun setLocationDataList(list: List<LocationDBData>) {
        locationDataList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentDataViewHolder {
        val binding =
            ItemRecentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecentDataViewHolder(binding)
    }

    override fun getItemCount(): Int = locationDataList.size

    override fun onBindViewHolder(holder: RecentDataViewHolder, position: Int) {
        holder.bind(locationDataList[position])
        holder.layout.setOnClickListener { view: View ->
            view.findNavController().navigate(
                RecentFragmentDirections.actionRecentFragmentToMainFragment(
                    convertLocationDataList(locationDataList[position])
                )
            )
        }
    }

    class RecentDataViewHolder(private val binding: ItemRecentBinding) :
        RecyclerView.ViewHolder(binding.root) {

        var layout = binding.linearLayout

        fun bind(locationDBData: LocationDBData) {
            binding.address = locationDBData.centerAddress
            binding.locations = getLocationsName(locationDBData.locations)
        }

        fun getLocationsName(locationDataList: List<LocationData>) : String{
            var locationsName = ""
            locationDataList.forEach { locationData ->
                if(locationsName.isNotEmpty()) {
                    locationsName += " - " + locationData.name
                }
                else{
                    locationsName += locationData.name
                }
            }
            return locationsName
        }
    }

    private fun convertLocationDataList(locationDBData : LocationDBData) : LocationDataList{
        val locationDataList = LocationDataList()
        locationDBData.locations.forEach { locationData ->
            locationDataList.add(locationData)
        }
        return locationDataList
    }
}