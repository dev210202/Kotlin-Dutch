package com.dutch2019.ui.main

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.dutch2019.R
import com.dutch2019.adapter.MainRecyclerAdapter
import com.dutch2019.base.BaseFragment
import com.dutch2019.databinding.FragmentMainBinding
import com.dutch2019.model.LocationData
import com.dutch2019.model.LocationDataList
import com.dutch2019.util.*
import com.kakao.sdk.common.util.Utility
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding>(
    R.layout.fragment_main
) {

    var count = 0
    private val vm: MainViewModel by activityViewModels()
    private val mainAdapter by lazy {
        MainRecyclerAdapter(onLocationSearchButtonClicked = { itemPosition ->
            vm.setSelectedItemIndex(itemPosition)
            view!!.findNavController().navigate(
                MainFragmentDirections.actionMainFragmentToSearchFragment(
                    locationdbdatalist = vm.getRecentLocationList()
                        .convertLocationDBDataToDataList()
                )
            )
        }, onLocationCloseButtonClicked = { position ->
            vm.removeAtLocationList(position)
        }, onLocationAddButtonClicked = {
            vm.addLocation(LocationData())
        }).apply {
            setLocationDataList(vm.getLocationList())
        }
    }

    override fun onViewCreated(view : View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        MainFragmentArgs.fromBundle(requireArguments()).locationdbdata.apply {
            if(this.isNotNull()){
                val loadedList =this!!.locations.toMutableList()
                loadedList.add(LocationData())
                vm.setLocationList(loadedList)
            }
        }

        binding.recyclerviewMain.apply {
            adapter = mainAdapter
        }

        vm.locationList.observe(viewLifecycleOwner) { list ->
            mainAdapter.setLocationDataList(list)
            binding.recyclerviewMain.scrollToPosition(mainAdapter.getLocationDataList().size - 1)

            if (isExistTwoOrMoreLocation()) {
                setButtonState(binding.btnFindMiddlelocation, ButtonState.ACTIVE)
            } else {
                setButtonState(binding.btnFindMiddlelocation, ButtonState.DISABLE)
            }
        }

        binding.imagebuttonMainLogo.setOnClickListener {
            checkHashKey()
        }


        binding.btnFindMiddlelocation.setOnClickListener {
            val locationList = vm.getLocationList()

            if (isExistTwoOrMoreLocation()) {
                checkFindMiddleLocationAvailable(locationList)
            } else {
                context?.toast("위치를 2개 이상 설정해주세요!")
            }
        }
    }

    private fun checkFindMiddleLocationAvailable(locationList: List<LocationData>) {
        if (checkNetWorkStatus(requireContext()) == NetWorkStatus.NONE) {
            context?.toast("인터넷 연결을 확인해주세요!")
        } else {
            view!!.findNavController().navigate(
                MainFragmentDirections.actionMainFragmentToMiddleFragment(
                    LocationDataList().convertLocationData(locationList)
                )
            )
        }
    }

    private fun isExistTwoOrMoreLocation(): Boolean {
        var count = 0
        mainAdapter.getLocationDataList().forEach { locationData ->
            if (locationData.name.isNotEmpty()) {
                count++
                if (count >= 2) {
                    return true
                }
            }
        }
        return false
    }

    private fun checkHashKey() {
        if (count == 5) {
            var keyHash = Utility.getKeyHash(requireContext())
            context?.toast("Key Hash : " + keyHash)
        } else {
            count++
        }
    }
}