package com.dutch2019.extension

import android.widget.CheckBox
import androidx.databinding.BindingAdapter
import com.dutch2019.adapter.DeleteRecentRecyclerAdapter
import com.dutch2019.adapter.RecentDetailRecyclerAdapter
import com.dutch2019.base.BaseViewModel
import com.dutch2019.model.LocationInfo
import kotlinx.android.synthetic.main.fragment_delete_recent.view.*

@BindingAdapter(value = ["selectallcheckbox"])
fun selectAllCheckBox(checkBox: CheckBox, viewModel: BaseViewModel) {
    checkBox.rootView.recyclerview.adapter = DeleteRecentRecyclerAdapter()
    checkBox.setOnCheckedChangeListener { _, _ ->
        (checkBox.rootView.recyclerview.adapter as DeleteRecentRecyclerAdapter).selectAllCheckBox()
        (checkBox.rootView.recyclerview.adapter as DeleteRecentRecyclerAdapter).notifyDataSetChanged()
    }

}
