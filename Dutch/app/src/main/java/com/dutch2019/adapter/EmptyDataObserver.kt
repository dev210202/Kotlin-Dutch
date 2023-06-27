package com.dutch2019.adapter

import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import com.dutch2019.util.isNotNull
import org.w3c.dom.Text

class EmptyDataObserver(private val rv: RecyclerView, private val tv: TextView) :
    RecyclerView.AdapterDataObserver() {
    private fun checkEmpty() {
        if (rv.adapter is SearchRecyclerAdapter) {
            tv.text = "검색된 결과가 없습니다."
        }
        if (rv.adapter!!.itemCount == 0) {
            tv.visibility = View.VISIBLE
        } else {
            tv.visibility = View.INVISIBLE
        }
    }


    override fun onChanged() {
        super.onChanged()
        Log.e("DATA CHANGEd", "!!")
        checkEmpty()
    }
}