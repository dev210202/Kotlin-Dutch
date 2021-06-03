package com.dutch2019.factory

import androidx.recyclerview.widget.RecyclerView
import com.dutch2019.adapter.*

public class RecyclerAdapterFactory : AdapterFactory() {
    override fun createAdapter(name: String): RecyclerView.Adapter<*> {
        lateinit var adapter: RecyclerView.Adapter<*>
        when (name) {
            "createButton" -> {
                adapter = DynamicButtonRecyclerAdapter()
            }
            "searchLocation" -> {
                adapter = SearchRecyclerAdapter()
            }
            "ratio" -> {
                adapter = RatioRecyclerAdapter()
            }
            "nearFacility" -> {
                adapter = NearRecyclerAdapter()
            }
            "recent" -> {
                adapter = RecentRecyclerAdapter()
            }
            "deleteRecent" -> {
                adapter = DeleteRecentRecyclerAdapter()
            }
        }
        return adapter
    }
}