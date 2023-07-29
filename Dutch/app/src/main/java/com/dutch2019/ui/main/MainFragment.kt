package com.dutch2019.ui.main

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
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

    private var count = 0
    private val vm: MainViewModel by activityViewModels()
    private val mainAdapter by lazy {
        MainRecyclerAdapter(onLocationSearchButtonClicked = { itemPosition ->
            vm.setSelectedItemIndex(itemPosition)
            findNavController().navigate(
                MainFragmentDirections.actionMainFragmentToSearchFragment()
            )
        }, onLocationCloseButtonClicked = { position ->
            vm.removeAtLocationList(position)
        }, onLocationAddButtonClicked = {
            vm.addLocation(LocationData())
        }).apply {
            setLocationDataList(vm.getLocationList())
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerviewMain.apply {
            adapter = mainAdapter
        }

        vm.locationList.observe(viewLifecycleOwner) { list ->
            mainAdapter.run{
                setLocationDataList(list)
                binding.recyclerviewMain.scrollToPosition(this.getLocationDataList().size - 1)
            }
            if (isExistTwoOrMoreLocation()) {
                ButtonState.ACTIVE.changeButton(binding.btnFindMiddlelocation)
             } else {
                ButtonState.DISABLE.changeButton(binding.btnFindMiddlelocation)
            }
        }



        binding.imagebuttonMainLogo.setOnClickListener {
            checkHashKey()
        }


        binding.btnFindMiddlelocation.setOnClickListener {
            if (isExistTwoOrMoreLocation()) {
                if (isAvailableFindMiddleLocation()) {
                    findNavController().navigate(
                        MainFragmentDirections.actionMainFragmentToMiddleFragment(
                            locationlist = LocationDataList().convertLocationData(
                                list = vm.getLocationList()
                            )
                        )
                    )
                } else {
                    context?.toast("인터넷 연결을 확인해주세요!")
                }
            } else {
                context?.toast("위치를 2개 이상 설정해주세요!")
            }
        }
    }

    private fun isAvailableFindMiddleLocation() =
        (checkNetWorkStatus(requireContext()) != NetWorkStatus.NONE)

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
            val keyHash = Utility.getKeyHash(requireContext())
            context?.toast("Key Hash : $keyHash")
            Log.i("key hash", keyHash)
        } else {
            count++
        }
    }
}