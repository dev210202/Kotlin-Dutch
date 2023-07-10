package com.dutch2019.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dutch2019.databinding.ItemRecentEditBinding
import com.dutch2019.model.LocationDBData
import com.dutch2019.model.LocationData

class RecentEditRecyclerAdapter :
    RecyclerView.Adapter<RecentEditRecyclerAdapter.RecentEditDataViewHolder>() {

    private var locationDataList = listOf<LocationDBData>()
    private var checkList = ArrayList<Boolean>()
    private var isAllSelectChecked = false

    fun selectAllCheckBox() {
        if(isAllSelectChecked) {
            for (i in checkList.indices) {
                checkList[i] = false
                isAllSelectChecked = false
            }
        }
        else{
            for (i in checkList.indices) {
                checkList[i] = true
                isAllSelectChecked = true
            }
        }
        for(i in 0 until checkList.size){
            Log.i("CHECKLISt : ", i.toString() + "check : " + checkList[i])
        }
    }

    fun getDeleteList(): List<LocationDBData> {
        val deleteList = arrayListOf<LocationDBData>()
        for (i in 0 until checkList.size) {
            Log.i("checkList T or F", "" + checkList[i] )
            if (checkList[i]) {
                deleteList.add(locationDataList[i])
            }
        }
        deleteList.forEach {
            Log.i("deleteListdata", it.centerAddress )
        }
        return deleteList
    }

    fun setLocationDataList(list: List<LocationDBData>) {
        locationDataList = list
        var newCheckList = ArrayList<Boolean>()
        notifyDataSetChanged()
        locationDataList.forEach { 
            newCheckList.add(false)
        }
        checkList = newCheckList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentEditDataViewHolder {
        val binding =
            ItemRecentEditBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecentEditDataViewHolder(binding)
    }

    override fun getItemCount(): Int = locationDataList.size

    override fun onBindViewHolder(holder: RecentEditDataViewHolder, position: Int) {
        holder.bind(locationDataList[position], position)
    }

    inner class RecentEditDataViewHolder(private val binding: ItemRecentEditBinding) :
        RecyclerView.ViewHolder(binding.root) {

        var layout = binding.layoutRecentEdit
        var checkboxLayout = binding.layoutCheckbox
        var checkbox = binding.checkbox

        fun bind(locationDBData: LocationDBData, position: Int) {
            binding.address = locationDBData.centerAddress
            binding.locations = getLocationsName(locationDBData.locations)
            checkboxLayout.setOnClickListener {
                checkbox.isChecked
            }
            checkbox.isChecked = checkList[position]
            checkbox.setOnClickListener {
                checkList[position] = checkbox.isChecked
                for(i in 0 until checkList.size){
                    Log.i("CHECK click : ", i.toString() + "check : " + checkList[i])
                }
            }
        }

        fun getLocationsName(locationDataList: List<LocationData>) : String{
            var locationsName = ""
            locationDataList.forEach { locationData ->
                if(locationsName.isNotEmpty()) {
                    locationsName += " - " + locationData.name
                }
                else{
                    locationsName += locationData.name
                }
            }
            return locationsName
        }
    }

}