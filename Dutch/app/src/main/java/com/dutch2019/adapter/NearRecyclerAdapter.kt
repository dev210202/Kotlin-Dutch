package com.dutch2019.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dutch2019.databinding.ItemNearBinding
import com.dutch2019.model.LocationData
import com.dutch2019.util.*

class NearRecyclerAdapter(
    private val onItemClicked : (LocationData) -> Unit
) :
    RecyclerView.Adapter<NearRecyclerAdapter.NearViewHolder>() {


    private var selectedPosition = -1
    private var locationDataList = mutableListOf<LocationData>()

    fun setLocationDataList(list: List<LocationData>) {
        locationDataList = list.toMutableList()
        notifyDataSetChanged()

    }

    fun setSelectedPosition(position: Int){
        val beforePosition = selectedPosition
        selectedPosition = position
        notifyItemChanged(position)
        notifyItemChanged(beforePosition)

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NearViewHolder {
        val binding =
            ItemNearBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NearViewHolder(binding, parent.context)
    }

    override fun getItemCount(): Int = locationDataList.size


    override fun onBindViewHolder(holder: NearViewHolder, position: Int) {
        holder.bind(locationDataList[position])
        holder.layout.setOnClickListener {
            setSelectedPosition(holder.adapterPosition)
            onItemClicked(locationDataList[position])
        }
        if(selectedPosition == position){
            holder.setBackgroundSelected()
        }
        else{
            holder.setBackgroundDefault()
        }
    }

    inner class NearViewHolder(
        private val binding: ItemNearBinding,
        private val context: Context
    ) :
        RecyclerView.ViewHolder(binding.root) {
         val layout = binding.layoutNear
        fun bind(locationData: LocationData) {
            binding.name = locationData.name
            binding.address = locationData.address
        }
        fun setBackgroundSelected(){
            binding.layoutNear.setBackgroundColor(getSelectedBackgroundColor(context))
            binding.tvNameItem.setTextColor(getPrimaryTextColor(context))
        }
        fun setBackgroundDefault(){
            binding.layoutNear.setBackgroundColor(getDefaultBackgroundColor(context))
            binding.tvNameItem.setTextColor(getActiveCTAButtonTextColor(context))
        }
    }
}
