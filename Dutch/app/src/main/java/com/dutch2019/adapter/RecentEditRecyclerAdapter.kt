package com.dutch2019.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dutch2019.databinding.ItemRecentEditBinding
import com.dutch2019.model.LocationData
import com.dutch2019.util.isNotNull

class RecentEditRecyclerAdapter() :
    RecyclerView.Adapter<RecentEditRecyclerAdapter.RecentEditDataViewHolder>() {

    private var locationDataList = listOf<LocationData>()
    private var checkList = mutableListOf<LocationData>()
    private var isSelectedAll = false
    fun setLocationDataList(list: List<LocationData>) {
        locationDataList = list
        notifyDataSetChanged()
    }

    fun getCheckList() = checkList

    fun selectAllCheckBox(isSelectState: (Boolean) -> Unit) {
        isSelectedAll = !isSelectedAll
        isSelectState(isSelectedAll)
        notifyItemRangeChanged(0, locationDataList.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentEditDataViewHolder {
        ItemRecentEditBinding.inflate(LayoutInflater.from(parent.context), parent, false).apply {
            return RecentEditDataViewHolder(this)
        }
    }

    override fun getItemCount(): Int = locationDataList.size

    override fun onBindViewHolder(holder: RecentEditDataViewHolder, position: Int) {
        holder.apply {
            checkbox.isChecked = isSelectedAll
            bind(locationDataList[position], position)
        }
    }

    inner class RecentEditDataViewHolder(
        private val binding: ItemRecentEditBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        val checkbox = binding.checkbox
        fun bind(locationData: LocationData, position: Int) {
            binding.locationData = locationData
            if (checkbox.isChecked) {
                if (checkList.find { data -> data == locationData } == null) {
                    checkList.add(locationDataList[position])
                }
            } else {
                checkList.remove(locationDataList[position])
            }
            binding.layoutRecentEdit.setOnClickListener {
                checkbox.isChecked = !checkbox.isChecked
                if (checkbox.isChecked) {
                    checkList.add(locationDataList[position])
                } else {
                    checkList.remove(locationDataList[position])
                }
            }
        }
    }

}