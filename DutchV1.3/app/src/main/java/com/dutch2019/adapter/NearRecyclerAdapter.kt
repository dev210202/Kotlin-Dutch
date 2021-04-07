package com.dutch2019.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.location_list_item.view.*
import com.dutch2019.R
import com.dutch2019.databinding.LocationListItemBinding
import com.dutch2019.model.LocationInfo
import com.dutch2019.ui.search.SearchLocationFragmentDirections

class NearRecyclerAdapter(var dataList: LiveData<ArrayList<LocationInfo>>) :
    RecyclerView.Adapter<NearRecyclerAdapter.NearViewHolder>() {



    private var locationDataList = ArrayList<LocationInfo>()

    fun setLocationDataList(list: ArrayList<LocationInfo>) {
        locationDataList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NearViewHolder {
        val binding =
            LocationListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NearViewHolder(binding)
    }

    override fun getItemCount(): Int = locationDataList.size


    override fun onBindViewHolder(holder: NearViewHolder, position: Int) {
        holder.bind(locationDataList[position])
        holder.layout.setOnClickListener { view : View ->
            view.findNavController().navigate(
                SearchLocationFragmentDirections.actionSearchLocationFragmentToLocationCheckFragment(
                    locationDataList[position]
                )
            )
        }
    }

    class NearViewHolder(private val binding: LocationListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        var layout = binding.linearLayout
        fun bind(locationInfo: LocationInfo) {
            binding.locationinfo = locationInfo

        }
    }
}