package com.dutch2019.ui.main

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.dutch2019.base.BaseFragment
import com.kakao.sdk.common.util.Utility
import dagger.hilt.android.AndroidEntryPoint
import com.dutch2019.R
import com.dutch2019.adapter.MainRecyclerAdapter
import com.dutch2019.databinding.FragmentMainBinding
import com.dutch2019.model.LocationDataList
import com.dutch2019.util.NetWorkStatus
import com.dutch2019.util.checkNetWorkStatus

@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding>(
    R.layout.fragment_main
) {

    private val mainViewModel: MainViewModel by activityViewModels()
    var count = 0
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        var list = MainFragmentArgs.fromBundle(requireArguments()).locationdatalist
        if(list != null){
            mainViewModel.setCheckLocationList(list)
        }

        binding.recyclerviewMain.adapter = MainRecyclerAdapter()

        mainViewModel.checkLocationList.observe(
            viewLifecycleOwner,
            { list ->
                (binding.recyclerviewMain.adapter as MainRecyclerAdapter).setLocationSetList(list)
                list.forEach {
                    Log.i("main value", it.name)
                }
            }
        )

        binding.imagebuttonMainLogo.setOnClickListener {
            if(count == 5){
                var keyHash = Utility.getKeyHash(requireContext())
                Toast.makeText(requireContext(), "Key Hash : " + keyHash, Toast.LENGTH_LONG).show()

            }
            else{
                count++
            }
        }

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
            }
            else if(checkNetWorkStatus(requireContext()) == NetWorkStatus.NONE){
                Toast.makeText(context, "인터넷 연결을 확인해주세요!", Toast.LENGTH_LONG).show()
            }
            else {
                view.findNavController().navigate(
                    MainFragmentDirections.actionMainFragmentToMiddleFragment(
                        LocationDataList().convertLocationData(locationSetList)
                    )
                )
            }
        }
    }
}