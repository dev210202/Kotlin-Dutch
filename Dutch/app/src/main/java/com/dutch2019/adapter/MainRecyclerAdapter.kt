package com.dutch2019.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dutch2019.databinding.ItemMainLocationAddButtonBinding
import com.dutch2019.databinding.ItemMainLocationSearchButtonBinding
import com.dutch2019.model.LocationData

class MainRecyclerAdapter(
    private val onLocationSearchButtonClicked: () -> Unit,
    private val onLocationAddButtonClicked: () -> Unit,
    private val onLocationCloseButtonClicked: (position : Int) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var locationDataList = mutableListOf<LocationData>()

    fun setLocationDataList(list: List<LocationData>) {
        locationDataList = list.toMutableList()
        notifyDataSetChanged()
    }

    fun getLocationDataList(): List<LocationData> {
        return locationDataList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            1 -> {
                val view = ItemMainLocationSearchButtonBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
                LocationSearchViewHolder(view, onLocationSearchButtonClicked, onLocationCloseButtonClicked)
            }
            else -> {
                val view = ItemMainLocationAddButtonBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
                LocationAddViewHolder(view, onLocationAddButtonClicked)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (locationDataList.lastIndex == position) 0 else 1
    }

    override fun getItemCount(): Int = locationDataList.size


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
       when(holder){
           is LocationSearchViewHolder -> {
                holder.bind(locationDataList[position], position)
           }
           is LocationAddViewHolder -> {

           }
       }
    }

    inner class LocationSearchViewHolder(
        private val binding: ItemMainLocationSearchButtonBinding,
        private val onLocationSearchButtonClicked: () -> Unit,
        private val onLocationCloseButtonClicked: (position : Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(locationData: LocationData, position: Int) {
            binding.locationData = locationData
            binding.tvMarkerNumber.text = (position + 1).toString()
            binding.btnClose.setOnClickListener {
                onLocationCloseButtonClicked(position)
            }
            binding.layoutSearch.setOnClickListener {
                onLocationSearchButtonClicked()
            }
        }
    }

    class LocationAddViewHolder(
        private val binding: ItemMainLocationAddButtonBinding,
        private val onLocationAddButtonClicked: () -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.layoutLocationAdd.setOnClickListener {
                onLocationAddButtonClicked()
            }
        }
    }
}