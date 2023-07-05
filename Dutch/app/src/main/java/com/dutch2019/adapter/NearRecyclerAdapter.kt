package com.dutch2019.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.dutch2019.databinding.ItemNearBinding
import com.dutch2019.model.LocationData
import com.dutch2019.ui.near.NearFragmentDirections
import com.dutch2019.util.*

class NearRecyclerAdapter(
) :
    RecyclerView.Adapter<NearRecyclerAdapter.NearViewHolder>() {

    private var selectedPosition = -1
    private var locationDataList = mutableListOf<LocationData>()

    fun setLocationDataList(list: List<LocationData>) {
        locationDataList = list.toMutableList()
        notifyDataSetChanged()
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
            val beforePosition = selectedPosition
            selectedPosition = holder.adapterPosition
            notifyItemChanged(position)
            notifyItemChanged(beforePosition)
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
            binding.tvNameItem.setTextColor(getSelectedTextColor(context))
        }
        fun setBackgroundDefault(){
            binding.layoutNear.setBackgroundColor(getDefaultBackgroundColor(context))
            binding.tvNameItem.setTextColor(getActiveTextColor(context))
        }
    }
}
