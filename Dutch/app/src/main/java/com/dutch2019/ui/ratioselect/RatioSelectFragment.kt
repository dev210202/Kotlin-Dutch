package com.dutch2019.ui.ratioselect

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.dutch2019.R
import com.dutch2019.base.BaseFragment
import com.dutch2019.databinding.FragmentRatioSelectBinding
import com.dutch2019.model.LocationData
import com.dutch2019.model.LocationDataList
import com.dutch2019.ui.middle.MiddleFragmentArgs
import com.dutch2019.ui.middle.MiddleViewModel
import com.dutch2019.util.ButtonState
import com.dutch2019.util.convertLocationDBDataToDataList
import com.dutch2019.util.getCalculatedRatioPoint
import com.dutch2019.util.marker.*
import com.dutch2019.util.setButtonState
import com.skt.Tmap.TMapPoint
import com.skt.Tmap.TMapView


class RatioSelectFragment :
    BaseFragment<FragmentRatioSelectBinding>(R.layout.fragment_ratio_select) {

    private val vm: MiddleViewModel by activityViewModels()
    private lateinit var  tMapView :TMapView
    private lateinit var ratioPoint: TMapPoint
    private var ratioLocationA = LocationData()
    private var ratioLocationB = LocationData()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        RatioSelectFragmentArgs.fromBundle(requireArguments()).let { data ->
            ratioLocationA= data.ratioA
            ratioLocationB= data.ratioB
        }

        tMapView = TMapView(context)
        markMiddleLocation(tMapView, requireContext(), vm.getCenterPoint())
        markSelectRatioLocation(
            tMapView, requireContext(), ratioLocationA, ratioLocationB
        )
        mapAutoZoom(tMapView, vm.getLocationList(), vm.getCenterPoint())
        markRatioLocation(tMapView, requireContext(), vm.getCenterPoint())
        drawLine(
            tMapView,
            ratioLocationA.convertTMapPoint(),
            ratioLocationB.convertTMapPoint()
        )
        binding.layoutRatioSelect.addView(tMapView)
        binding.ratio = "5 : 5"

        binding.btnRatioSetComplete.setOnClickListener {
            vm.setRatioPoint(ratioPoint)
            findNavController().navigate(RatioSelectFragmentDirections.actionRatioSelectFragmentToMiddleFragment(
                LocationDataList().convertLocationData(vm.getLocationList())))
        }
        binding.seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, ratio: Int, p2: Boolean) {
                binding.ratio = "${(ratio +1)} : ${(9 - ratio)}"
                ratioPoint = getCalculatedRatioPoint(
                    ratioLocationA.convertTMapPoint(),
                    ratioLocationB.convertTMapPoint(),
                    ratio + 1
                )
                markRatioLocation(
                    tMapView, requireContext(), ratioPoint
                )
                if (!binding.btnRatioSetComplete.isSelected) {
                    setButtonState(binding.btnRatioSetComplete, ButtonState.ACTIVE)
                }
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }

        })
    }
}