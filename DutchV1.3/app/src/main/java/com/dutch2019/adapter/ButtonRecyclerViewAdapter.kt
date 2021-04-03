package com.dutch2019.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dutch2019.databinding.ButtonRecyclerItemBinding
import com.dutch2019.model.LocationInfo
import com.dutch2019.ui.search.SearchLocationActivity

class ButtonRecyclerViewAdapter :
    RecyclerView.Adapter<ButtonRecyclerViewAdapter.ButtonViewHolder>() {

    var locationInfoData = ArrayList<LocationInfo>()

    fun setLocationData(list: ArrayList<LocationInfo>) {
        locationInfoData = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ButtonViewHolder {
        val binding =
            ButtonRecyclerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ButtonViewHolder(binding)
    }

    override fun getItemCount(): Int = locationInfoData.size


    override fun onBindViewHolder(holder: ButtonViewHolder, position: Int) {
        holder.locationButton.setOnClickListener {
            val intent = Intent(holder.locationButton.context, SearchLocationActivity::class.java)
            holder.locationButton.context.startActivity(intent)
        }
        holder.minusButton.setOnClickListener {
            locationInfoData.removeAt(position)
            notifyDataSetChanged()
        }
        holder.bind(locationInfoData[position])
    }

    class ButtonViewHolder(private val binding: ButtonRecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var minusButton = binding.minusButton
        var locationButton = binding.locationButton
        fun bind(locationInfo: LocationInfo) {

            Log.i("Location", locationInfo.name);
            binding.locationInfo = locationInfo
        }
    }

}