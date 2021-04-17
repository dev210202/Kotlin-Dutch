package com.dutch2019.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.dutch2019.base.BaseRecyclerAdapter
import com.dutch2019.base.BaseViewHolder
import com.dutch2019.databinding.DeleteRecentDetailListItemBinding
import com.dutch2019.databinding.DeleteRecentListItemBinding
import com.dutch2019.databinding.RecentDetailListItemBinding
import com.dutch2019.model.LocationDataDB
import com.dutch2019.model.LocationInfo
import com.dutch2019.ui.recent.RecentFragmentDirections

class DeleteRecentDetailRecyclerAdapter :  BaseRecyclerAdapter() {
    override lateinit var baseList : List<Object>


    override fun setRecyclerList(list: List<Object>) {
        baseList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val binding =   DeleteRecentDetailListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {

    }

//    private var locationInfoList = listOf<LocationInfo>()
//
//    fun setLocationDataDB(list: List<LocationInfo>) {
//        locationInfoList = list
//        notifyDataSetChanged()
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeleteRecentDetailViewHolder {
//        val binding =
//            DeleteRecentDetailListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//        return DeleteRecentDetailViewHolder(binding)
//    }
//
//    override fun getItemCount(): Int = locationInfoList.size
//
//    override fun onBindViewHolder(holder: DeleteRecentDetailViewHolder, position: Int) {
//        holder.bind(locationInfoList[position])
//
//    }
//
//    class DeleteRecentDetailViewHolder(private val binding: DeleteRecentDetailListItemBinding) :
//        RecyclerView.ViewHolder(binding.root) {
//        fun bind(locationInfo: LocationInfo) {
//            binding.locationInfo = locationInfo
//        }
//    }
}