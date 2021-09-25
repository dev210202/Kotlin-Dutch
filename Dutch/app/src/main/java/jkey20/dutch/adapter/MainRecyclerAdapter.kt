package jkey20.dutch.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import jkey20.dutch.databinding.ItemDynamicbuttonBinding
import jkey20.dutch.model.LocationData

class MainRecyclerAdapter :
    RecyclerView.Adapter<MainRecyclerAdapter.ButtonViewHolder>() {

    private var locationSetList = ArrayList<LocationData>()

    fun setLocationSetList(list: ArrayList<LocationData>) {
        locationSetList = list
        notifyDataSetChanged()
    }

    fun getLocationSetList(): ArrayList<LocationData> {
        return locationSetList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ButtonViewHolder {
        val binding =
            ItemDynamicbuttonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ButtonViewHolder(binding)
    }

    override fun getItemCount(): Int = locationSetList.size


    override fun onBindViewHolder(holder: ButtonViewHolder, position: Int) {


        holder.numberTextView.text = "" + (position + 1)
        holder.bind(locationSetList[position])
        holder.removeButton.setOnClickListener {
            locationSetList.removeAt(position)
            notifyDataSetChanged()
        }
    }

    class ButtonViewHolder(private val binding: ItemDynamicbuttonBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var numberTextView = binding.textviewDynamicNumber
        var removeButton = binding.imagebuttonDynamicRemove
        fun bind(locationData: LocationData) {
            binding.locationData = locationData
        }
    }
}