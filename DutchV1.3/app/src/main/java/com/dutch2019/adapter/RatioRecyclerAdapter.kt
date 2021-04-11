package com.dutch2019.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.recyclerview.widget.RecyclerView
import com.dutch2019.R
import com.dutch2019.model.LocationInfo
import com.skt.Tmap.TMapPoint
import kotlinx.android.synthetic.main.ratio_recycler_item.view.*
import kotlin.collections.ArrayList


class RatioRecyclerAdapter(var dataArray: ArrayList<LocationInfo>) :
    RecyclerView.Adapter<RatioRecyclerAdapter.ViewHolder>() {

    var pointArrayList = ArrayList<TMapPoint>()
    var pointA = TMapPoint(0.0, 0.0)
    var pointB = TMapPoint(0.0, 0.0)
    var isAChecked = false
    var isBChecked = false

    class ViewHolder constructor(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.ratio_recycler_item, parent, false)
    ) {
        val numberTextView: TextView = itemView.number_textview
        val locationTextView: TextView = itemView.location_textview
        val checkBox: AppCompatCheckBox? = itemView.checkbox
        val pointTextView: TextView = itemView.choose_textview
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(parent)

    override fun getItemCount(): Int = dataArray.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.numberTextView.text = "" + (position + 1) + "번 위치"
        holder.locationTextView.text =
            dataArray[position].name
        val context = holder.itemView.context
        holder.checkBox?.setOnClickListener {

            if (holder.checkBox.isChecked) {

                if (isAChecked) {
                    if (isBChecked) {
                        holder.checkBox.isChecked = false
                        Toast.makeText(context, "비율 조정은 2개만 가능합니다", Toast.LENGTH_LONG).show()

                    } else {
                        holder.pointTextView.text = "B"
                        isBChecked = true
                        pointB =
                            TMapPoint(dataArray[position].latitude, dataArray[position].longitude)
                    }
                } else {
                    holder.pointTextView.text = "A"
                    isAChecked = true
                    pointA = TMapPoint(dataArray[position].latitude, dataArray[position].longitude)
                }
            } else {
                if (holder.pointTextView.text == "A") {
                    holder.pointTextView.text = " "
                    isAChecked = false
                } else if (holder.pointTextView.text == "B") {
                    holder.pointTextView.text = " "
                    isBChecked = false
                }
            }


        }
    }
    fun getRatioPointArrayList(): ArrayList<TMapPoint> {

        return if(isAChecked && isBChecked){
            pointArrayList.add(pointA)
            pointArrayList.add(pointB)
            Log.e("PointArray", pointArrayList.toString())
            pointArrayList
        } else{
            ArrayList<TMapPoint>()
        }
    }

}