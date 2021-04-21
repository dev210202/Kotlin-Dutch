package com.dutch2019.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dutch2019.databinding.DeleteRecentListItemBinding
import com.dutch2019.model.LocationDataDB

class DeleteRecentRecyclerAdapter :
    RecyclerView.Adapter<DeleteRecentRecyclerAdapter.RecentViewHolder>() {

    private var locationDataDB = listOf<LocationDataDB>()
    private var checkMap = HashMap<Int, Boolean>()
    private var isAllSelectChecked = false

    fun setLocationDataDB(list: List<LocationDataDB>) {
        locationDataDB = list
        notifyDataSetChanged()
    }

    fun selectAllCheckBox() {
        isAllSelectChecked = if (isClickedSelectAllCheckBox()) {
            for (i in 0 until checkMap.size) {
                checkMap.replace(i, false)
            }
            false
        } else {
            for (i in 0 until checkMap.size) {
                checkMap.replace(i, true)
            }
            true
        }
    }

    fun getDeleteList(): ArrayList<LocationDataDB> {
        val deleteList = ArrayList<LocationDataDB>()
        for(i in 0 until checkMap.size){
            if(checkMap[i]!!){
                deleteList.add(locationDataDB[i])
            }
        }
        return deleteList
    }

    fun isClickedSelectAllCheckBox() : Boolean = isAllSelectChecked

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentViewHolder {
        val binding =
            DeleteRecentListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecentViewHolder(binding)
    }

    override fun getItemCount(): Int = locationDataDB.size

    override fun onBindViewHolder(holder: RecentViewHolder, position: Int) {
        holder.bind(locationDataDB[position], position)
    }

    inner class RecentViewHolder(private val binding: DeleteRecentListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private var checkbox = binding.checkBox
        fun bind(locationData: LocationDataDB, position: Int) {
            binding.locationdata = locationData
            val adapter = DeleteRecentDetailRecyclerAdapter()
            binding.recyclerview.adapter = adapter
            (binding.recyclerview.adapter as DeleteRecentDetailRecyclerAdapter).setLocationDataDB(
                locationData.list
            )
            binding.recyclerview.adapter?.notifyDataSetChanged()

            if (position >= checkMap.size) {
                checkMap[position] = isAllSelectChecked
            }
            checkbox.isChecked = checkMap[position]!!
            checkbox.setOnClickListener {
                checkMap[position] = checkbox.isChecked
            }
        }
    }
}