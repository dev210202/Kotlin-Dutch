package com.dutch2019.base

import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.dutch2019.adapter.RecentDetailRecyclerAdapter
import com.dutch2019.adapter.RecentRecyclerAdapter
import com.dutch2019.databinding.RecentListItemBinding
import com.dutch2019.model.LocationDataDB
import java.util.*

abstract class BaseRecyclerAdapter() : RecyclerView.Adapter<BaseRecyclerAdapter.BaseViewHolder>()  {
    abstract var baseList : List<Object>
    lateinit var binding : ViewDataBinding


    abstract fun setRecyclerList(list : List<Object>)



    override fun getItemCount(): Int = baseList.size

    fun setBind(bind : ViewDataBinding){
        binding = bind
    }
    abstract class BaseViewHolder(binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root){
        abstract fun bind()
    }
}