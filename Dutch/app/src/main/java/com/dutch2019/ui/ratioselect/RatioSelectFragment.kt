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
import com.dutch2019.ui.middle.MiddleViewModel
import com.dutch2019.util.ButtonState
import com.dutch2019.util.marker.*
import com.dutch2019.util.setButtonState
import com.skt.Tmap.TMapPoint
import com.skt.Tmap.TMapView


class RatioSelectFragment :
    BaseFragment<FragmentRatioSelectBinding>(R.layout.fragment_ratio_select) {

    private val vm: MiddleViewModel by activityViewModels()
    private val tMapView by lazy { TMapView(context) }
    private lateinit var ratioPoint : TMapPoint
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        markMiddleLocation(tMapView, requireContext(), vm.getCenterPoint())
        markSelectRatioLocation(
            tMapView, requireContext(), vm.getRatioLocationA(), vm.getRatioLocationB()
        )
        mapAutoZoom(tMapView, vm.getLocationList(), vm.getSearchPoint())
        markRatioLocation(tMapView, requireContext(), vm.getCenterPoint())
        drawLine(
            tMapView,
            vm.getRatioLocationA().convertTMapPoint(),
            vm.getRatioLocationB().convertTMapPoint()
        )
        binding.layoutRatioSelect.addView(tMapView)

        binding.btnRatioSetComplete.setOnClickListener {
            vm.setRatioPoint(ratioPoint)
            findNavController().apply {
                popBackStack()
                popBackStack()
            }

        }
        binding.seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, ratio: Int, p2: Boolean) {
                binding.ratio = ((ratio + 1).toString() + " : " + (9 - ratio))
                ratioPoint = vm.getCalculatedRatioPoint(
                    vm.getRatioLocationA().convertTMapPoint(),
                    vm.getRatioLocationB().convertTMapPoint(),
                    ratio + 1
                )
                markRatioLocation(
                    tMapView, requireContext(), ratioPoint
                )
                if (!binding.btnRatioSetComplete.isSelected) {
                    setButtonState(binding.btnRatioSetComplete, ButtonState.DEFAULT)
                }
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }

        })
        // 비율 변경지점을 중간지점에 찍음

        // seekbar 조정시 비율 변경지점이 변경되게
        // seekbar listener -> 비율 변경
    }
}