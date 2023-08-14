package com.dutch2019.adapter

import android.view.LayoutInflater
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dutch2019.databinding.ItemSearchBinding
import com.dutch2019.model.LocationData
import com.dutch2019.util.convertTMapPOIItemToLocationData
import com.dutch2019.util.filtNull
import com.skt.Tmap.poi_item.TMapPOIItem

class SearchRecyclerAdapter(
    private val onRightArrowButtonClicked: (LocationData) -> Unit,
) : RecyclerView.Adapter<SearchRecyclerAdapter.LocationDataViewHolder>() {

    private var locationDataList = mutableListOf<LocationData>()

    fun setLocationItemList(list: List<LocationData>) {
        locationDataList = list.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationDataViewHolder {
        ItemSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false).apply {
            return LocationDataViewHolder(this, onRightArrowButtonClicked)
        }
    }

    override fun getItemCount(): Int = locationDataList.size

    override fun onBindViewHolder(holder: LocationDataViewHolder, position: Int) {
        holder.bind(locationDataList[position])
    }

    class LocationDataViewHolder(
        private val binding: ItemSearchBinding,
        private val onRightArrowButtonClicked: (LocationData) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(locationData: LocationData) {
            binding.name = locationData.name
            binding.address = filtNull(locationData.address)
            binding.layoutSearch.setOnClickListener {
                onRightArrowButtonClicked(locationData)
            }
        }
    }
}
