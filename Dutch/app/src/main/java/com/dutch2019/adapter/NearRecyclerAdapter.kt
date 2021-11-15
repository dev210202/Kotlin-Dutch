package com.dutch2019.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.dutch2019.databinding.ItemNearBinding
import com.dutch2019.model.LocationData
import com.dutch2019.ui.near.NearFragmentDirections

class NearRecyclerAdapter() :
    RecyclerView.Adapter<NearRecyclerAdapter.NearViewHolder>() {


    private var locationDataList = ArrayList<LocationData>()

    fun setLocationDataList(list: ArrayList<LocationData>) {
        locationDataList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NearViewHolder {
        val binding =
            ItemNearBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NearViewHolder(binding)
    }

    override fun getItemCount(): Int = locationDataList.size


    override fun onBindViewHolder(holder: NearViewHolder, position: Int) {
        holder.bind(locationDataList[position])
        holder.infoButton.setOnClickListener { view ->
            view.findNavController().navigate(
                NearFragmentDirections.actionNearFragmentToInfoFragment(
                    locationDataList[position]
                )
            )
        }
    }

    class NearViewHolder(private val binding: ItemNearBinding) :
        RecyclerView.ViewHolder(binding.root) {

        var infoButton = binding.infoButton
        fun bind(locationData: LocationData) {
            binding.locationdata = locationData
        }
    }
}
