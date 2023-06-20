package com.dutch2019.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.dutch2019.R
import com.dutch2019.databinding.ItemSearchBinding
import com.dutch2019.model.LocationData
import com.dutch2019.ui.search.SearchFragmentDirections
import com.dutch2019.util.filtNull
import com.skt.Tmap.poi_item.TMapPOIItem

class SearchRecyclerAdapter :
    RecyclerView.Adapter<SearchRecyclerAdapter.LocationDataViewHolder>() {

    private var locationDataList = ArrayList<TMapPOIItem>()

    fun setLocationDataList(list: ArrayList<TMapPOIItem>) {
        locationDataList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationDataViewHolder {
        val binding =
            ItemSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LocationDataViewHolder(binding)
    }

    override fun getItemCount(): Int = locationDataList.size

    override fun onBindViewHolder(holder: LocationDataViewHolder, position: Int) {
        holder.bind(locationDataList[position])
        holder.layout.setOnClickListener { view: View ->
            val imm =
                view.rootView.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(
                view.rootView.findViewById<EditText>(R.id.edittext_search).windowToken,
                0
            )

            view.findNavController().navigate(
                SearchFragmentDirections.actionSearchFragmentToLocationCheckFragment(
                   LocationData(
                       locationDataList[position].poiid,
                       locationDataList[position].poiName,
                       filtNull(locationDataList[position].poiAddress),
                       "" + locationDataList[position].telNo,
                       locationDataList[position].noorLat.toDouble(),
                       locationDataList[position].noorLon.toDouble()
                   )
                )
            )
        }
    }

    class LocationDataViewHolder(private val binding: ItemSearchBinding) :
        RecyclerView.ViewHolder(binding.root) {

        var layout = binding.linearLayout

        fun bind(tMapPOIItem: TMapPOIItem) {
            binding.name = tMapPOIItem.name
            binding.address = filtNull(tMapPOIItem.poiAddress)
        }
    }
}
