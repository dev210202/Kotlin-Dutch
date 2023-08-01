package com.dutch2019.ui.ratioselect

import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.dutch2019.R
import com.dutch2019.base.BaseFragment
import com.dutch2019.databinding.FragmentRatioSelectBinding
import com.dutch2019.model.LocationData
import com.dutch2019.model.LocationDataList
import com.dutch2019.ui.middle.MiddleViewModel
import com.dutch2019.util.*
import com.dutch2019.util.marker.*
import com.skt.Tmap.TMapPoint
import com.skt.Tmap.TMapView


class RatioSelectFragment :
    BaseFragment<FragmentRatioSelectBinding>(R.layout.fragment_ratio_select) {

    private val vm: MiddleViewModel by activityViewModels()
    private lateinit var tMapView: TMapView
    private lateinit var ratioPoint: TMapPoint
    private var ratioA = LocationData()
    private var ratioB = LocationData()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ratio = "5 : 5"

        initRatioAandB()
        initTMapView()
        initButtonRatioSetComplete()
        initSeekBar()
    }

    private fun initSeekBar() {
        binding.seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, ratio: Int, p2: Boolean) {
                binding.ratio = "${(ratio + 1)} : ${(9 - ratio)}"
                ratioPoint = getCalculatedRatioPoint(
                    ratioA.convertTMapPoint(), ratioB.convertTMapPoint(), ratio + 1
                )
                markRatioLocation(tMapView, requireContext(), ratioPoint)
                if (binding.btnRatioSetComplete.isNotSelected()) {
                    ButtonState.ACTIVE.changeButton(binding.btnRatioSetComplete)
                }
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }

        })
    }

    private fun initButtonRatioSetComplete() {
        binding.btnRatioSetComplete.setOnClickListener {
            vm.setRatioPoint(ratioPoint)
            findNavController().navigate(
                RatioSelectFragmentDirections.actionRatioSelectFragmentToMiddleFragment(
                    locationlist = LocationDataList().convertLocationData(vm.getLocationList())
                )
            )
        }
    }

    private fun initTMapView() {
        tMapView = TMapView(context).apply {
            markMiddleLocation(this, requireContext(), vm.getCenterPoint())
            markSelectRatioLocation(this, requireContext(), ratioA, ratioB)
            mapAutoZoom(this, vm.getLocationList(), vm.getCenterPoint())
            markRatioLocation(
                this,
                requireContext(),
                calculateCenterPoint(ratioA.convertTMapPoint(), ratioB.convertTMapPoint())
            )
            drawLine(this, ratioA.convertTMapPoint(), ratioB.convertTMapPoint())
            binding.layoutRatioSelect.addView(this)
        }
    }

    private fun initRatioAandB() {
        RatioSelectFragmentArgs.fromBundle(requireArguments()).let { data ->
            ratioA = data.ratioA
            ratioB = data.ratioB
        }
    }
}