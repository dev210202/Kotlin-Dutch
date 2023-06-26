package com.dutch2019.ui.main

import android.os.Bundle
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.dutch2019.R
import com.dutch2019.adapter.MainRecyclerAdapter
import com.dutch2019.base.BaseFragment
import com.dutch2019.databinding.FragmentMainBinding
import com.dutch2019.model.LocationData
import com.dutch2019.model.LocationDataList
import com.dutch2019.ui.search.SearchFragmentArgs
import com.dutch2019.util.NetWorkStatus
import com.dutch2019.util.checkNetWorkStatus
import com.dutch2019.util.convertLocationDBDataToDataList
import com.dutch2019.util.toast
import com.kakao.sdk.common.util.Utility
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding>(
    R.layout.fragment_main
) {

    private val vm: MainViewModel by activityViewModels()
    var count = 0
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.recyclerviewMain.apply {
            adapter = MainRecyclerAdapter(
                onLocationSearchButtonClicked = {
                    view!!.findNavController().navigate(
                        MainFragmentDirections.actionMainFragmentToSearchFragment(
                            locationdbdatalist = vm.getRecentLocationList().convertLocationDBDataToDataList()
                        )
                    )
                }, onLocationCloseButtonClicked = { position ->
                    vm.removeAtLocationList(position)
                }, onLocationAddButtonClicked = {
                    vm.addLocation(LocationData())
                    scrollToPosition((this.adapter as MainRecyclerAdapter).getLocationDataList().size - 1)
                }
            ).apply {
                setLocationDataList(vm.getLocationList())
            }
        }

        vm.locationList.observe(viewLifecycleOwner) { list ->
            (binding.recyclerviewMain.adapter as MainRecyclerAdapter).setLocationDataList(list)
        }

        binding.imagebuttonMainLogo.setOnClickListener {
            checkHashKey()
        }


        binding.btnFindMiddlelocation.setOnClickListener {
            val locationList = vm.getLocationList()

            if (isExistTwoOrMoreLocation(locationList)) {
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

    private fun isExistTwoOrMoreLocation(locationList: List<LocationData>): Boolean {
        var locationCount = 0
        locationList.forEach { locationData ->
            if (locationCount >= 2) {
                return@forEach
            }
            if (locationData.lat != 0.0 && locationData.lon != 0.0) {
                locationCount++
            }
        }
        if (locationCount < 2) {
            return true
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