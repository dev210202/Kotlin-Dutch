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
import com.dutch2019.util.getLocationsName

class RecentRecyclerAdapter(
) : RecyclerView.Adapter<RecentRecyclerAdapter.RecentDataViewHolder>() {

    private var locationDataList = listOf<LocationDBData>()

    fun setLocationDataList(list: List<LocationDBData>) {
        locationDataList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentDataViewHolder {
        val binding = ItemRecentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecentDataViewHolder(binding)
    }

    override fun getItemCount(): Int = locationDataList.size

    override fun onBindViewHolder(holder: RecentDataViewHolder, position: Int) {
        holder.bind(locationDataList[position])
    }

    class RecentDataViewHolder(
        private val binding: ItemRecentBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        val layout = binding.layoutRecent
        fun bind(locationDBData: LocationDBData) {
            binding.address = locationDBData.centerAddress
            binding.locations = locationDBData.locations.getLocationsName()
            // TODO : 리스트 아이템 클릭시 메인에 위치리스트에 데이터 세팅
        }
    }
}