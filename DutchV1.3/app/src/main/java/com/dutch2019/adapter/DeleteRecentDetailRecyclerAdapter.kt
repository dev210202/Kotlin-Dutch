package com.dutch2019.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dutch2019.databinding.DeleteRecentDetailListItemBinding
import com.dutch2019.model.LocationInfo

class DeleteRecentDetailRecyclerAdapter :  RecyclerView.Adapter<DeleteRecentDetailRecyclerAdapter.DeleteRecentDetailViewHolder>() {

    private var locationInfoList = listOf<LocationInfo>()

    fun setLocationDataDB(list: List<LocationInfo>) {
        locationInfoList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeleteRecentDetailViewHolder {
        val binding =
            DeleteRecentDetailListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DeleteRecentDetailViewHolder(binding)
    }

    override fun getItemCount(): Int = locationInfoList.size

    override fun onBindViewHolder(holder: DeleteRecentDetailViewHolder, position: Int) {
        holder.bind(locationInfoList[position])

    }

    class DeleteRecentDetailViewHolder(private val binding: DeleteRecentDetailListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(locationInfo: LocationInfo) {
            binding.locationInfo = locationInfo
        }
    }
}