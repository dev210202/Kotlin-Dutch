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
        list.forEachIndexed { index, locationData ->
            Log.e("List ITEM $index", locationData.toString())

        }
        notifyDataSetChanged()
    }

    fun getCheckList() = checkList
    fun clearCheckList() = checkList.clear()

    fun selectAllCheckBox(isSelectState: (Boolean) -> Unit) {
        isSelectedAll = !isSelectedAll
        isSelectState(isSelectedAll)
        if(isSelectedAll){
            checkList = locationDataList.toMutableList()
        }
        else{
            checkList.clear()
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentEditDataViewHolder {
        ItemRecentEditBinding.inflate(LayoutInflater.from(parent.context), parent, false).apply {
            return RecentEditDataViewHolder(this)
        }
    }

    override fun getItemCount(): Int = locationDataList.size

    override fun onBindViewHolder(holder: RecentEditDataViewHolder, position: Int) {
        holder.bind(locationDataList[position], position)

    }

    inner class RecentEditDataViewHolder(
        private val binding: ItemRecentEditBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

       fun bind(locationData: LocationData, position: Int) {
            binding.locationData = locationData

            binding.checkbox.isChecked = isSelectedAll

            binding.layoutRecentEdit.setOnClickListener {
                binding.checkbox.isChecked = !binding.checkbox.isChecked
                if (binding.checkbox.isChecked) {
                    checkList.add(locationDataList[position])
                } else {
                    checkList.remove(locationDataList[position])
                }
            }

        }
    }

}