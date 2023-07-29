package com.dutch2019.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dutch2019.databinding.ItemMainLocationAddButtonBinding
import com.dutch2019.databinding.ItemMainLocationSearchButtonBinding
import com.dutch2019.model.LocationData
import com.dutch2019.util.Color
import com.dutch2019.util.marker.Marker
import com.dutch2019.util.setDefaultLocationItem

class MainRecyclerAdapter(
    private val onLocationSearchButtonClicked: (position: Int) -> Unit,
    private val onLocationAddButtonClicked: () -> Unit,
    private val onLocationCloseButtonClicked: (position: Int) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var locationDataList = mutableListOf<LocationData>()

    fun setLocationDataList(list: List<LocationData>) {
        locationDataList = list.toMutableList()
        notifyDataSetChanged()
    }

    fun getLocationDataList(): List<LocationData> = locationDataList


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            1 -> {
                ItemMainLocationSearchButtonBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                ).run {
                    LocationSearchViewHolder(
                        this,
                        onLocationSearchButtonClicked,
                        onLocationCloseButtonClicked,
                        parent.context
                    )
                }
            }
            else -> {
                ItemMainLocationAddButtonBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                ).run {
                    LocationAddViewHolder(this, onLocationAddButtonClicked)
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (locationDataList.lastIndex == position) 0 else 1
    }

    override fun getItemCount(): Int = locationDataList.size


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is LocationSearchViewHolder -> {
                holder.bind(position)
            }
        }
    }

    inner class LocationSearchViewHolder(
        private val binding: ItemMainLocationSearchButtonBinding,
        private val onLocationSearchButtonClicked: (position: Int) -> Unit,
        private val onLocationCloseButtonClicked: (position: Int) -> Unit,
        private val context: Context
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.locationData = locationDataList[position]
            binding.tvMarkerNumber.text = "${position +1}"
            if (locationDataList[position].name.isNotEmpty()) {
                binding.tvName.text = locationDataList[position].name
                binding.tvName.setTextColor(Color.TEXT_ACTIVE.getColor(context))
                binding.layoutMarker.background = Marker.CHANGED.getMark(context)
            } else {
                /*
                   이 코드가 없으면 recycler view item에 유효한 위치가 있을때,
                   view를 재활용하는 경우 유효한 위치가 저장된 view를 재활용하면서 다시 if문을 거치게되는데, else가 없으면 그대로 변경된 ui가 표시됨.
                 */
                setDefaultLocationItem(context, binding.tvName, binding.layoutMarker)
            }
            binding.btnClose.setOnClickListener {
                onLocationCloseButtonClicked(position)
            }
            binding.layoutSearch.setOnClickListener {
                onLocationSearchButtonClicked(position)
            }
        }
    }

    class LocationAddViewHolder(
        private val binding: ItemMainLocationAddButtonBinding,
        private val onLocationAddButtonClicked: () -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.layoutLocationAdd.setOnClickListener {
                onLocationAddButtonClicked()
            }
        }
    }
}