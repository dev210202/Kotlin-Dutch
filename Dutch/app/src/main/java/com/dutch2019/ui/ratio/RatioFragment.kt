package com.dutch2019.ui.ratio

import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.dutch2019.R
import com.dutch2019.base.BaseFragment
import com.dutch2019.databinding.FragmentRatioBinding
import com.dutch2019.model.LocationData
import com.dutch2019.ui.middle.MiddleViewModel
import com.dutch2019.util.ButtonState
import com.dutch2019.util.getActiveTextColor
import com.dutch2019.util.marker.*
import com.dutch2019.util.setButtonState
import com.dutch2019.util.setDefaultLocationItem
import com.skt.Tmap.TMapView

class RatioFragment : BaseFragment<FragmentRatioBinding>(
    R.layout.fragment_ratio
) {
    private val vm: MiddleViewModel by activityViewModels()
    private lateinit var tMapView: TMapView
    private lateinit var markerItemABitmap: Bitmap
    private lateinit var markerItemBBitmap: Bitmap
    private var ratioLocationA = LocationData()
    private var ratioLocationB = LocationData()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tMapView = TMapView(context)
        markLocationList(tMapView, requireContext(), vm.getLocationList())
        mapAutoZoom(tMapView, vm.getLocationList(), vm.getCenterPoint())
        setMarkerClickEvent(tMapView)

        binding.layoutRatio.addView(tMapView)
        binding.layoutCloseA.setOnClickListener(OnCloseClickListener())
        binding.layoutCloseB.setOnClickListener(OnCloseClickListener())

        binding.btnRatioComplete.setOnClickListener { button ->
            if (button.isSelected) {
                findNavController().navigate(RatioFragmentDirections.actionRatioFragmentToRatioSelectFragment(
                    ratioA = ratioLocationA,
                    ratioB = ratioLocationB
                ))
            }
        }
    }

    inner class OnCloseClickListener : OnClickListener {
        override fun onClick(view: View?) {
            var id = ""
            lateinit var previousIconBitmap: Bitmap
            lateinit var textView: TextView
            lateinit var markerLayout: FrameLayout
            when (view) {
                binding.layoutCloseA -> {
                    id = binding.tvNameA.text.toString()
                    textView = binding.tvNameA
                    markerLayout = binding.layoutMarkerA
                    previousIconBitmap = markerItemABitmap
                    binding.layoutCloseA.visibility = INVISIBLE
                }
                binding.layoutCloseB -> {
                    id = binding.tvNameB.text.toString()
                    textView = binding.tvNameB
                    markerLayout = binding.layoutMarkerB
                    previousIconBitmap = markerItemBBitmap
                    binding.layoutCloseB.visibility = INVISIBLE
                }
            }

            val currentMarkerItem = tMapView.getMarkerItem2FromID(id)

            currentMarkerItem.icon = previousIconBitmap
            setDefaultLocationItem(requireContext(), textView, markerLayout)
            removeAllBallon(tMapView)
            setButtonState(binding.btnRatioComplete, ButtonState.DISABLE)
        }

    }

    private fun setMarkerClickEvent(tMapView: TMapView) {
        tMapView.setOnMarkerClickEvent { _, tMapMarkerItem2 ->
            var location = LocationData()
            vm.getLocationList().forEach { locationData ->
                if (locationData.isEqualToTMapMarkerItem2(tMapMarkerItem2)) {
                    location = locationData
                }
            }

            if (isNotSetRatioLocationA()) {
                markerItemABitmap = tMapMarkerItem2.icon
                changeSelectRatioMark(tMapMarkerItem2, requireContext())
                drawTextOnMarker(requireContext(), tMapMarkerItem2.icon, "A")
                setLayoutRatioLocationView(
                    name = location.name,
                    textView = binding.tvNameA,
                    markerLayout = binding.layoutMarkerA,
                    closeLayout = binding.layoutCloseA
                )
                ratioLocationA = location

            } else if (isNotSetRatioLocationB()) {
                markerItemBBitmap = tMapMarkerItem2.icon
                changeSelectRatioMark(tMapMarkerItem2, requireContext())
                drawTextOnMarker(requireContext(), tMapMarkerItem2.icon, "B")
                setLayoutRatioLocationView(
                    name = location.name,
                    textView = binding.tvNameB,
                    markerLayout = binding.layoutMarkerB,
                    closeLayout = binding.layoutCloseB
                )
                ratioLocationB = location
            } else {
                // Toast
            }

            if (isSetLocationAandB()) {
                setButtonState(binding.btnRatioComplete, ButtonState.ACTIVE)
            }
        }
    }


    private fun setLayoutRatioLocationView(
        name: String, textView: TextView, markerLayout: ViewGroup, closeLayout: ViewGroup
    ) {
        textView.text = name
        textView.setTextColor(getActiveTextColor(requireContext()))
        markerLayout.background = getSelectRatioMarkerBackground(requireContext())
        closeLayout.visibility = VISIBLE
    }

    private fun isSetLocationAandB() = binding.tvNameA.text != "위치를 입력해주세요" && binding.tvNameB.text != "위치를 입력해주세요"

    private fun isNotSetRatioLocationA() = binding.tvNameA.text == "위치를 입력해주세요"

    private fun isNotSetRatioLocationB() = binding.tvNameB.text == "위치를 입력해주세요"

}