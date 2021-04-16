package com.dutch2019.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.dutch2019.databinding.RecentDetailListItemBinding
import com.dutch2019.databinding.RecentListItemBinding
import com.dutch2019.model.LocationDataDB
import com.dutch2019.model.LocationInfo
import com.dutch2019.ui.recent.RecentFragmentDirections

class RecentDetailRecyclerAdapter :
    RecyclerView.Adapter<RecentDetailRecyclerAdapter.RecentDetailViewHolder>() {

    private var locationInfoList = listOf<LocationInfo>()

    fun setLocationDataDB(list: List<LocationInfo>) {
        locationInfoList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentDetailViewHolder {
        val binding =
            RecentDetailListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecentDetailViewHolder(binding)
    }

    override fun getItemCount(): Int = locationInfoList.size

    override fun onBindViewHolder(holder: RecentDetailViewHolder, position: Int) {
        holder.bind(locationInfoList[position])
        holder.layout.setOnClickListener { view ->
            var locationDataDB = LocationDataDB()
            locationDataDB.list = locationInfoList
            view.findNavController()
                .navigate(RecentFragmentDirections.actionRecentFragmentToMainFragment(locationDataDB))


        }
    }

    class RecentDetailViewHolder(private val binding: RecentDetailListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var layout = binding.detailLayout
        fun bind(locationInfo: LocationInfo) {
            binding.locationInfo = locationInfo
        }
    }
}