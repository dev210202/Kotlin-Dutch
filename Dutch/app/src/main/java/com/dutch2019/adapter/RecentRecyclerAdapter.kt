package com.dutch2019.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dutch2019.databinding.ItemRecentBinding
import com.dutch2019.model.LocationSearchData
import com.dutch2019.util.getLocationsName

class RecentRecyclerAdapter(
    private val onRecentItemClicked : (LocationSearchData) -> Unit,
) : RecyclerView.Adapter<RecentRecyclerAdapter.RecentDataViewHolder>() {

    private var locationDataList = listOf<LocationSearchData>()

    fun setLocationDataList(list: List<LocationSearchData>) {
        locationDataList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentDataViewHolder {
        val binding = ItemRecentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecentDataViewHolder(binding, onRecentItemClicked)
    }

    override fun getItemCount(): Int = locationDataList.size

    override fun onBindViewHolder(holder: RecentDataViewHolder, position: Int) {
        holder.bind(locationDataList[position])
    }

    class RecentDataViewHolder(
        private val binding: ItemRecentBinding,
        private val onRecentItemClicked : (LocationSearchData) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {

        val layout = binding.layoutRecent
        fun bind(locationSearchData: LocationSearchData) {
            //binding.address = locationSearchData.centerAddress
            //binding.locations = locationSearchData.locations.getLocationsName()
            binding.layoutRecent.setOnClickListener {
                onRecentItemClicked(locationSearchData)
            }
        }
    }
}