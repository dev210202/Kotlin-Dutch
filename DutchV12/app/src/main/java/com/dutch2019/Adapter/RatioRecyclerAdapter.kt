package com.dutch2019.Adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.databinding.ObservableField
import androidx.recyclerview.widget.RecyclerView
import com.dutch2019.Data.LocationData
import com.dutch2019.R
import io.reactivex.Observable
import kotlinx.android.synthetic.main.ratio_recycler_item.view.*
import java.util.*
import kotlin.collections.ArrayList


class RatioRecyclerAdapter(var dataArray: ArrayList<LocationData>) :
    RecyclerView.Adapter<RatioRecyclerAdapter.ViewHolder>() {

    val ratioList = ArrayList<String>(Collections.nCopies(dataArray.size, " "))
    var isAChecked = false
    var isBChecked : false
    class ViewHolder constructor(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.ratio_recycler_item, parent, false)
    ) {
        val numberTextView: TextView = itemView.number_textview
        val locationTextView: TextView = itemView.location_textview
        val checkBox: AppCompatCheckBox? = itemView.checkbox
        val pointTextView : TextView = itemView.choose_textview
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        RatioRecyclerAdapter.ViewHolder(parent)

    override fun getItemCount(): Int = dataArray.size

    override fun onBindViewHolder(holder: RatioRecyclerAdapter.ViewHolder, position: Int) {
        holder.numberTextView.text = "" + (position + 1) + "번 위치"
        holder.locationTextView.text =
            dataArray[position].locationName
        val context = holder.itemView.context
//        holder.checkBox?.setOnClickListener {
//            if(!isAChecked){
//                    holder.pointTextView.text = "A"
//                    isAChecked = true
//            }
//            else if(holder.pointTextView.text == "A"){
//                if(isBChecked){
//
//                }
//                holder.pointTextView.text = ""
//                isAChecked = false
//            }
//            else{
//                if(!isBChecked){
//                    holder.pointTextView.text = "B"
//                    isBChecked = true
//                }
//                else if(holder.pointTextView.text ==  "B"){
//                    holder.pointTextView.text = ""
//                    isBChecked = false
//                }
//
//            }
//
//        }

    }

    fun getRatioArrayList(): ArrayList<String> {
        Log.e("RatioList", ratioList.get(0).toString() + "\n" + ratioList.get(1).toString() )
        return ratioList
    }


}