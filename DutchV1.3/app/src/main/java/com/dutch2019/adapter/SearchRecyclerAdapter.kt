package com.dutch2019.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.content.getSystemService
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.dutch2019.databinding.LocationListItemBinding
import com.dutch2019.model.LocationInfo
import com.dutch2019.ui.search.SearchLocationFragmentDirections
import kotlinx.android.synthetic.main.fragment_search_location.view.*

class SearchRecyclerAdapter :
    RecyclerView.Adapter<SearchRecyclerAdapter.LocationDataViewHolder>() {

    private var locationDataList = ArrayList<LocationInfo>()

    fun setLocationDataList(list: ArrayList<LocationInfo>) {
        locationDataList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationDataViewHolder {
        val binding =
            LocationListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LocationDataViewHolder(binding)
    }

    override fun getItemCount(): Int = locationDataList.size

    override fun onBindViewHolder(holder: LocationDataViewHolder, position: Int) {
        holder.bind(locationDataList[position])
        holder.layout.setOnClickListener { view : View ->
            val imm = view.rootView.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.rootView.inputedittext.windowToken, 0)
            view.findNavController().navigate(

                SearchLocationFragmentDirections.actionSearchLocationFragmentToLocationCheckFragment(
                    locationDataList[position]
                )
            )
        }
    }

    class LocationDataViewHolder(private val binding: LocationListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        var layout = binding.linearLayout
        fun bind(locationInfo: LocationInfo) {
            binding.locationinfo = locationInfo
        }
    }

}