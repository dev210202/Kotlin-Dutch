package com.dutch2019.ui.deleterecent

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.dutch2019.R
import com.dutch2019.adapter.DeleteRecentRecyclerAdapter
import com.dutch2019.base.BaseFragment
import com.dutch2019.databinding.FragmentDeleteRecentBinding
import com.dutch2019.ui.recent.RecentViewModel

class DeleteRecentFragment : BaseFragment<FragmentDeleteRecentBinding, RecentViewModel>(
    R.layout.fragment_delete_recent,
    RecentViewModel::class.java
) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.initList()
        viewModel.initDB(requireActivity().application)
        viewModel.getRecentLocationDB()


        viewModel.locationList.observe(this, Observer { list ->
            if (binding.recyclerview.adapter != null) {
                (binding.recyclerview.adapter as DeleteRecentRecyclerAdapter).setLocationDataDB(list)
            }
        })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.chooseAllCheckBox.setOnCheckedChangeListener { buttonView, isChecked ->

            (binding.recyclerview.adapter as DeleteRecentRecyclerAdapter).selectAllCheckBox()
            (binding.recyclerview.adapter as DeleteRecentRecyclerAdapter).notifyDataSetChanged()
        }

        binding.completeButton.setOnClickListener {
            view ->
            var deleteList =
                (binding.recyclerview.adapter as DeleteRecentRecyclerAdapter).getDeleteList()
            deleteList.forEach {
                it->
                it.list.forEach {
                    Log.i("DeleteRecentFragment completebutton", it.name)
                }
            }
            viewModel.deleteLocationDB(deleteList)
            view.findNavController().popBackStack()
        }

    }
}