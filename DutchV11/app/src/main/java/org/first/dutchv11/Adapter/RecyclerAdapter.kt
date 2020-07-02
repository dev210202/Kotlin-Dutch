package org.first.dutchv11.Adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.location_list_item.view.*
import org.first.dutchv11.Data.LocationData
import org.first.dutchv11.LocationCheckActivity
import org.first.dutchv11.R

class RecyclerAdapter(var context: Context, var dataList: LiveData<ArrayList<LocationData>>) :
    RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(parent)

    override fun getItemCount(): Int = dataList.value!!.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        dataList.value!!.get(position).let { item ->
            with(holder) {
                locationNameTextView.text = item.locationName
                locationAddressTextView.text = item.locationAddress
                Log.e("locationsetting", item.locationName + "\n" + item.locationAddress)
            }
        }
//        holder.itemView.rightArrowButton.setOnClickListener {
//            Log.e("TEST", "" + position)
//            if (position != RecyclerView.NO_POSITION) {
//                var item = dataList.value!!.get(position)
//
//                var intent = Intent(context, LocationCheckActivity::class.java)
//                intent.putExtra("locationName", item.locationName)
//                intent.putExtra("locationAddress", item.locationAddress)
//                intent.putExtra("latitude", item.latitude)
//                intent.putExtra("longitude", item.longitude)
//
//                Log.e(
//                    "TEST@",
//                    item.locationName + item.locationAddress + item.latitude + item.longitude
//                )
//
//                context.startActivity(intent)
//
//            }
//        }

        fun getLatitude(position: Int): Double = dataList.value!!.get(position).latitude
        fun getLongitude(position: Int): Double = dataList.value!!.get(position).longitude

    }

    class ViewHolder constructor(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.location_list_item, parent, false)
    ) {
        val locationNameTextView = itemView.locationnametextview
        val locationAddressTextView = itemView.locationaddresstextview
    }

}