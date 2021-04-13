package com.dutch2019.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.dutch2019.databinding.RecentListItemBinding
import com.dutch2019.model.LocationDataDB
import com.dutch2019.model.LocationInfo
import com.dutch2019.ui.recent.RecentFragmentDirections

class RecentRecyclerAdapter : RecyclerView.Adapter<RecentRecyclerAdapter.RecentViewHolder>() {

    private var locationDataDB = listOf<LocationDataDB>()

    fun setLocationDataDB(list: List<LocationDataDB>) {
        locationDataDB = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentViewHolder {
        val binding =
            RecentListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecentViewHolder(binding)
    }

    override fun getItemCount(): Int = locationDataDB.size

    override fun onBindViewHolder(holder: RecentViewHolder, position: Int) {
        holder.bind(locationDataDB[position])
        holder.rightArrowButton.setOnClickListener {
            view ->
        view.findNavController().navigate(RecentFragmentDirections.actionRecentFragmentToMainFragment(locationDataDB[position]))
        }
    }

    class RecentViewHolder(private val binding: RecentListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var rightArrowButton = binding.rightArrowButton
        fun bind(locationData: LocationDataDB) {
            binding.locationdata = locationData

            var adapter = RecentDetailRecyclerAdapter()
            binding.recyclerview.adapter = adapter
            (binding.recyclerview.adapter as RecentDetailRecyclerAdapter).setLocationDataDB(
                locationData.list
            )
            binding.recyclerview.adapter?.notifyDataSetChanged()
        }
    }
}