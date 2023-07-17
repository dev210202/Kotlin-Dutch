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
        when(rv.adapter){
            is SearchRecyclerAdapter -> {
                tv.text = "검색된 결과가 없습니다."
            }
            is NearRecyclerAdapter -> {
                tv.text = "검색된 결과가 없습니다.\n 다른 카테고리를 선택해주세요"
            }
        }

        if (rv.adapter!!.itemCount == 0) {
            tv.visibility = View.VISIBLE
        } else {
            tv.visibility = View.INVISIBLE
        }
    }


    override fun onChanged() {
        super.onChanged()
        checkEmpty()
    }
}