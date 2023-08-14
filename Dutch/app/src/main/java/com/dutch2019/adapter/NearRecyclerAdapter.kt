package com.dutch2019.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dutch2019.databinding.ItemNearBinding
import com.dutch2019.model.LocationData
import com.dutch2019.util.*

class NearRecyclerAdapter(
    private val onItemClicked: (LocationData) -> Unit,
    private val onInternetClicked: (LocationData) -> Unit
) : RecyclerView.Adapter<NearRecyclerAdapter.NearViewHolder>() {


    private var selectedPosition = -1
    private var locationDataList = mutableListOf<LocationData>()

    fun setLocationDataList(list: List<LocationData>) {
        locationDataList = list.toMutableList()
        notifyDataSetChanged()

    }

    fun setSelectedPosition(position: Int) {
        val beforePosition = selectedPosition
        selectedPosition = position
        notifyItemChanged(position)
        notifyItemChanged(beforePosition)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NearViewHolder {
        ItemNearBinding.inflate(LayoutInflater.from(parent.context), parent, false).apply {
            return NearViewHolder(this, parent.context, onInternetClicked)
        }
    }

    override fun getItemCount(): Int = locationDataList.size


    override fun onBindViewHolder(holder: NearViewHolder, position: Int) {
        holder.run {
            bind(locationDataList[position])
            layout.setOnClickListener {
                setSelectedPosition(holder.adapterPosition)
                onItemClicked(locationDataList[position])
            }
            if (selectedPosition == position) {
                setBackgroundSelected()
            } else {
                setBackgroundDefault()
            }
        }
    }

    inner class NearViewHolder(
        private val binding: ItemNearBinding,
        private val context: Context,
        private val onInternetClicked: (LocationData) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {
        val layout = binding.layoutNear
        fun bind(locationData: LocationData) {
            locationData.apply {
                binding.locationData = this
                binding.layoutIbInternet.setOnClickListener{
                    onInternetClicked(this)
                }
            }
        }

        fun setBackgroundSelected() {
            binding.layoutNear.setBackgroundColor(Color.BACKGROUND_SELECTED.getColor(context))
            binding.tvNameItem.setTextColor(Color.TEXT_PRIMARY.getColor(context))
        }

        fun setBackgroundDefault() {
            binding.layoutNear.setBackgroundColor(Color.BACKGROUND_DEFAULT.getColor(context))
            binding.tvNameItem.setTextColor(Color.TEXT_WHITE.getColor(context))
        }
    }
}
