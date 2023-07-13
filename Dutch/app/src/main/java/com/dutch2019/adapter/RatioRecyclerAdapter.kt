package com.dutch2019.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.skt.Tmap.TMapPoint
import com.dutch2019.databinding.ItemRatioBinding
import com.dutch2019.model.LocationData
import com.dutch2019.util.getChangedTextColor
import com.dutch2019.util.getDefaultTextColor
import com.dutch2019.util.marker.getChangedtMarkerBackground
import com.dutch2019.util.marker.getDefaultMarkerBackground
import com.dutch2019.util.marker.getSelectRatioMarkerBackground
import kotlin.collections.ArrayList


class RatioRecyclerAdapter(
    private val onCloseButtonClicked: () -> Unit,
) : RecyclerView.Adapter<RatioRecyclerAdapter.RatioViewHolder>() {

    private var locationList = mutableListOf<LocationData>()
    init {
        locationList.add(LocationData())
        locationList.add(LocationData())
    }
    fun setLocationA(locationData: LocationData){
        locationList[0] = locationData
        notifyItemChanged(0)
    }
    fun setLocationB(locationData: LocationData){
        locationList[1] = locationData
        notifyItemChanged(1)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RatioViewHolder {
        var binding = ItemRatioBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RatioViewHolder(binding, parent.context, onCloseButtonClicked)
    }


    override fun getItemCount(): Int = locationList.size

    override fun onBindViewHolder(holder: RatioViewHolder, position: Int) {

        holder.bind(locationList[position], position)


    }


    inner class RatioViewHolder(
        val binding: ItemRatioBinding, val context: Context,
        val onCloseButtonClicked: () -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(locationData: LocationData, position: Int) {
            binding.locationData = locationData
            binding.btnClose.setOnClickListener {
                setDefault()
            }
            if (locationData.name.isEmpty()) {
                setDefault()
            } else {
                if (position == 0) {
                    binding.tvMarker.text = "A"
                } else {
                    binding.tvMarker.text = "B"
                }
                binding.tvName.setTextColor(getChangedTextColor(context))
                binding.layoutMarker.background = getSelectRatioMarkerBackground(context)
            }
        }

        fun setDefault(){
            setLocationA(LocationData())
            setLocationB(LocationData())
            binding.tvName.text = "위치를 입력해주세요."
            binding.tvName.setTextColor(getDefaultTextColor(context))
            binding.layoutMarker.background = getDefaultMarkerBackground(context)
        }
    }

}