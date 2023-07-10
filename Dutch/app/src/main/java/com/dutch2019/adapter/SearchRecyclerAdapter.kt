package com.dutch2019.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.dutch2019.R
import com.dutch2019.databinding.ItemSearchBinding
import com.dutch2019.model.LocationData
import com.dutch2019.model.Properties
import com.dutch2019.ui.search.SearchFragmentDirections
import com.dutch2019.util.convertTMapPOIItemToLocationData
import com.dutch2019.util.filtNull
import com.skt.Tmap.poi_item.TMapPOIItem

class SearchRecyclerAdapter(
    private val onRightArrowButtonClicked: (LocationData) -> Unit,
) : RecyclerView.Adapter<SearchRecyclerAdapter.LocationDataViewHolder>() {

    private var locationDataList = mutableListOf<TMapPOIItem>()

    fun setTMapPOIItemList(list: List<TMapPOIItem>) {
        locationDataList = list.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationDataViewHolder {
        val binding = ItemSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LocationDataViewHolder(binding, onRightArrowButtonClicked)
    }

    override fun getItemCount(): Int = locationDataList.size

    override fun onBindViewHolder(holder: LocationDataViewHolder, position: Int) {
        val tMapPOIItem = locationDataList[position]
        holder.bind(tMapPOIItem)
    }

    class LocationDataViewHolder(private val binding: ItemSearchBinding,
    private val onRightArrowButtonClicked: (LocationData) -> Unit) :
        RecyclerView.ViewHolder(binding.root) {

        val layoutSearch = binding.layoutSearch

        fun bind(tMapPOIItem: TMapPOIItem) {
            val locationData = convertTMapPOIItemToLocationData(tMapPOIItem)

            binding.name = tMapPOIItem.name
            binding.address = filtNull(tMapPOIItem.poiAddress)
            layoutSearch.setOnClickListener { view ->
                onRightArrowButtonClicked(locationData)
            }
        }
    }
}
