package com.dutch2019.adapter

import android.location.Location
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.CompoundButton.OnCheckedChangeListener
import androidx.recyclerview.widget.RecyclerView
import com.dutch2019.databinding.ItemRecentEditBinding
import com.dutch2019.model.LocationSearchData
import com.dutch2019.model.LocationData

class RecentEditRecyclerAdapter(private val onCheckBoxSelected: (LocationData, Boolean) -> Unit) :
    RecyclerView.Adapter<RecentEditRecyclerAdapter.RecentEditDataViewHolder>() {

    private var locationDataList = listOf<LocationData>()
    private var isSelectedAll = false
    fun setLocationDataList(list: List<LocationData>) {
        locationDataList = list
        notifyDataSetChanged()
    }

    fun selectAllCheckBox(checkAllSelected: (Boolean) -> Unit) {
        isSelectedAll = !isSelectedAll
        notifyDataSetChanged()
        checkAllSelected(isSelectedAll)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentEditDataViewHolder {
        val binding =
            ItemRecentEditBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecentEditDataViewHolder(binding, onCheckBoxSelected)
    }

    override fun getItemCount(): Int = locationDataList.size

    override fun onBindViewHolder(holder: RecentEditDataViewHolder, position: Int) {
        holder.checkbox.isChecked = isSelectedAll
        holder.bind(locationDataList[position])
    }

    inner class RecentEditDataViewHolder(
        private val binding: ItemRecentEditBinding,
        private val onCheckBoxSelected: (LocationData, Boolean) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        val checkbox = binding.checkbox
        fun bind(locationData: LocationData) {
            binding.name = locationData.name
            binding.address = locationData.address
            checkbox.setOnCheckedChangeListener { _, isChecked ->
                onCheckBoxSelected(locationData, isChecked)
            }
        }
    }


}