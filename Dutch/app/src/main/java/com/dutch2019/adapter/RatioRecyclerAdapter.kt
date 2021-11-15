package com.dutch2019.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.skt.Tmap.TMapPoint
import com.dutch2019.databinding.ItemRatioBinding
import com.dutch2019.model.LocationData
import kotlin.collections.ArrayList


class RatioRecyclerAdapter :
    RecyclerView.Adapter<RatioRecyclerAdapter.RatioViewHolder>() {


    var locationList = ArrayList<LocationData>()

    var pointA = TMapPoint(0.0, 0.0)
    var pointB = TMapPoint(0.0, 0.0)
    var isAChecked = false
    var isBChecked = false


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RatioViewHolder {
        var binding = ItemRatioBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RatioViewHolder(binding)
    }


    override fun getItemCount(): Int = locationList.size

    override fun onBindViewHolder(holder: RatioViewHolder, position: Int) {
        var checkBox = holder.binding.checkbox
        var chooseTextView = holder.binding.chooseTextview
        holder.bind(locationList[position], position)
        checkBox.setOnClickListener {


            if (checkBox.isChecked) {
                if (isAChecked) {
                    if (isBChecked) {
                        checkBox.isChecked = false
                    }
                    else {
                        chooseTextView.text = "B"
                        isBChecked = true
                        pointB = TMapPoint(
                            locationList[position].lat,
                            locationList[position].lon
                        )
                    }
                }
                else {
                    chooseTextView.text = "A"
                    isAChecked = true
                    pointA =
                        TMapPoint(locationList[position].lat, locationList[position].lon)
                }
            }
            else {
                if (chooseTextView.text == "A") {
                    chooseTextView.text = " "
                    isAChecked = false
                }
                else if (chooseTextView.text == "B") {
                    chooseTextView.text = " "
                    isBChecked = false
                }
            }

        }
    }

//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        holder.numberTextView.text = "" + (position + 1) + "번 위치"
//        holder.locationTextView.text =
//            dataArray[position].name
//        val context = holder.itemView.context
//        holder.checkBox?.setOnClickListener {
//
//            if (holder.checkBox.isChecked) {
//
//                if (isAChecked) {
//                    if (isBChecked) {
//                        holder.checkBox.isChecked = false
//                        Toast.makeText(context, "비율 조정은 2개만 가능합니다", Toast.LENGTH_LONG).show()
//
//                    } else {
//                        holder.pointTextView.text = "B"
//                        isBChecked = true
//                        pointB =
//                            TMapPoint(dataArray[position].latitude, dataArray[position].longitude)
//                    }
//                } else {
//                    holder.pointTextView.text = "A"
//                    isAChecked = true
//                    pointA = TMapPoint(dataArray[position].latitude, dataArray[position].longitude)
//                }
//            } else {
//                if (holder.pointTextView.text == "A") {
//                    holder.pointTextView.text = " "
//                    isAChecked = false
//                } else if (holder.pointTextView.text == "B") {
//                    holder.pointTextView.text = " "
//                    isBChecked = false
//                }
//            }
//
//
//        }
//    }

    class RatioViewHolder(var binding: ItemRatioBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(locationData: LocationData, position: Int) {
            binding.locationdata = locationData
            binding.numberTextview.text = "" + (position + 1) + "번 위치"

        }
    }

    fun setLocationListData(list: ArrayList<LocationData>) {
        locationList = list
        notifyDataSetChanged()
        locationList.forEach {
            data ->

            Log.i("LOCATION LIST", data.name)

        }
    }

    fun getRatioPointArrayList(): ArrayList<TMapPoint> {

        var pointArrayList = ArrayList<TMapPoint>()
        if (isAChecked && isBChecked) {
            pointArrayList.add(pointA)
            pointArrayList.add(pointB)
            Log.e("PointArray", pointArrayList.toString())
        }
        return pointArrayList
    }

    fun checkPoints() {

    }

}