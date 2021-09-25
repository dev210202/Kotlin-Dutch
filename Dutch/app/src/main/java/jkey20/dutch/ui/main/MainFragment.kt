package jkey20.dutch.ui.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.dutch2019.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import jkey20.dutch.R
import jkey20.dutch.adapter.MainRecyclerAdapter
import jkey20.dutch.databinding.FragmentMainBinding
import jkey20.dutch.model.LocationData
import jkey20.dutch.model.LocationDataList

@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding>(
    R.layout.fragment_main
) {

    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.recyclerviewMain.adapter = MainRecyclerAdapter()

        mainViewModel.checkLocationList.observe(
            viewLifecycleOwner,
            Observer { list ->
                (binding.recyclerviewMain.adapter as MainRecyclerAdapter).setLocationSetList(list)
                list.forEach {
                    Log.i("main value", it.name)
                }
            }
        )

        binding.layoutMainPlus.setOnClickListener { view ->
            view.findNavController().navigate(
                MainFragmentDirections.actionMainFragmentToSearchFragment()
            )
        }
        binding.imagebuttonMainPlus.setOnClickListener { view ->
            view.findNavController().navigate(
                MainFragmentDirections.actionMainFragmentToSearchFragment()
            )
        }
        binding.buttonMainSearch.setOnClickListener { view ->
            val locationSetList =
                (binding.recyclerviewMain.adapter as MainRecyclerAdapter).getLocationSetList()

            if (locationSetList.size < 2) {
                Toast.makeText(context, "위치를 2개 이상으로 설정해주세요!", Toast.LENGTH_LONG).show()
            } else {
                view.findNavController().navigate(
                    MainFragmentDirections.actionMainFragmentToMiddleFragment(
                        getRecyclerviewList(locationSetList)
                    )
                )
            }
        }
    }

    private fun getRecyclerviewList(list: ArrayList<LocationData>): LocationDataList {

        val locationDataList = LocationDataList()
        list.forEach { locationData ->
            locationDataList.add(locationData)
        }
        return locationDataList
    }

}