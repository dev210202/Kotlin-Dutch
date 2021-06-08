package com.dutch2019.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dutch2019.databinding.ItemDynamicbuttonBinding
import com.dutch2019.model.LocationInfo

class MainRecyclerAdapter :
    RecyclerView.Adapter<MainRecyclerAdapter.ButtonViewHolder>() {

    private var locationInfoData = ArrayList<LocationInfo>()

    fun setLocationData(list: ArrayList<LocationInfo>) {
        locationInfoData = list
        notifyDataSetChanged()
    }

    fun getLocationData(): ArrayList<LocationInfo> {
        return locationInfoData
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ButtonViewHolder {
        val binding =
            ItemDynamicbuttonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ButtonViewHolder(binding)
    }

    override fun getItemCount(): Int = locationInfoData.size


    override fun onBindViewHolder(holder: ButtonViewHolder, position: Int) {

        holder.minusButton.setOnClickListener {
            locationInfoData.removeAt(position)
            notifyDataSetChanged()
        }
        holder.bind(locationInfoData[position])
    }

    class ButtonViewHolder(private val binding: ItemDynamicbuttonBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var minusButton = binding.minusButton
        fun bind(locationInfo: LocationInfo) {
            binding.locationInfo = locationInfo
        }
    }

}