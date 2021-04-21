package com.dutch2019.extension

import android.widget.CheckBox
import androidx.databinding.BindingAdapter
import com.dutch2019.adapter.DeleteRecentRecyclerAdapter
import com.dutch2019.base.BaseViewModel
import kotlinx.android.synthetic.main.fragment_delete_recent.view.*

@BindingAdapter(value = ["selectallcheckbox"])
fun selectAllCheckBox(checkBox: CheckBox, viewModel : BaseViewModel) {
    val adapter = (checkBox.rootView.recyclerview.adapter as DeleteRecentRecyclerAdapter)
    checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
        adapter.selectAllCheckBox()
        adapter.notifyDataSetChanged()
    }
}
