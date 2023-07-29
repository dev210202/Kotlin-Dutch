package com.dutch2019.ui.ratio

import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.dutch2019.R
import com.dutch2019.base.BaseFragment
import com.dutch2019.databinding.FragmentRatioBinding
import com.dutch2019.model.LocationData
import com.dutch2019.ui.middle.MiddleViewModel
import com.dutch2019.util.*
import com.dutch2019.util.marker.*
import com.skt.Tmap.TMapMarkerItem2
import com.skt.Tmap.TMapView

class RatioFragment : BaseFragment<FragmentRatioBinding>(
    R.layout.fragment_ratio
) {
    private val vm: MiddleViewModel by activityViewModels()
    private lateinit var tMapView: TMapView
    private lateinit var markerItemAPreviousBitmap: Bitmap
    private lateinit var markerItemBPreviousBitmap: Bitmap
    private var ratioLocationA = LocationData()
    private var ratioLocationB = LocationData()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tMapView = TMapView(context).apply {
            markLocationList(this, requireContext(), vm.getLocationList())
            mapAutoZoom(this, vm.getLocationList(), vm.getCenterPoint())
            setMarkerClickEvent(this)
            binding.layoutRatio.addView(this)
        }


        binding.layoutCloseA.setOnClickListener(OnCloseClickListener())
        binding.layoutCloseB.setOnClickListener(OnCloseClickListener())

        binding.btnRatioComplete.setOnClickListener { button ->
            if (button.isSelected) {
                findNavController().navigate(
                    RatioFragmentDirections.actionRatioFragmentToRatioSelectFragment(
                        ratioA = ratioLocationA, ratioB = ratioLocationB
                    )
                )
            }
        }
    }

    inner class OnCloseClickListener : OnClickListener {
        override fun onClick(view: View?) {
            val objectToChange = object {
                var id = ""
                lateinit var previousIconBitmap: Bitmap
                lateinit var textView: TextView
                lateinit var markerLayout: FrameLayout

                fun setCloseA(){
                    id = binding.tvNameA.text.toString()
                    textView = binding.tvNameA
                    markerLayout = binding.layoutMarkerA
                    previousIconBitmap = markerItemAPreviousBitmap
                }

                fun setCloseB(){
                    id = binding.tvNameB.text.toString()
                    textView = binding.tvNameB
                    markerLayout = binding.layoutMarkerB
                    previousIconBitmap = markerItemBPreviousBitmap
                }
            }
            when (view) {
                binding.layoutCloseA -> {
                    objectToChange.setCloseA()
                    binding.layoutCloseA.visibility = INVISIBLE
                }
                binding.layoutCloseB -> {
                    objectToChange.setCloseB()
                    binding.layoutCloseB.visibility = INVISIBLE
                }
            }
            objectToChange.run {
                tMapView.getMarkerItem2FromID(id).icon = previousIconBitmap
                setDefaultLocationItem(requireContext(), textView, markerLayout)
            }
            removeAllBallon(tMapView)
            ButtonState.DISABLE.changeButton(binding.btnRatioComplete)
        }

    }

    private fun setMarkerClickEvent(tMapView: TMapView) {
        tMapView.setOnMarkerClickEvent { _, tMapMarkerItem2 ->
            val location = vm.getLocationList().filter { locationData ->
                locationData.isEqualToTMapMarkerItem2(tMapMarkerItem2)
            }[0]

            if (isNotSetRatioLocationA()) {
                setSelectedMarkerToRatioLocationA(tMapMarkerItem2,location)
                markerItemAPreviousBitmap = tMapMarkerItem2.icon
            } else if (isNotSetRatioLocationB()) {
                setSelectedMarkerToRatioLocationB(tMapMarkerItem2, location)
                markerItemBPreviousBitmap = tMapMarkerItem2.icon
            }

            if (isSetLocationAandB()) {
                ButtonState.ACTIVE.changeButton(binding.btnRatioComplete)
            }
        }
    }

    private fun setSelectedMarkerToRatioLocationB(tMapMarkerItem2: TMapMarkerItem2, location: LocationData) {
        tMapMarkerItem2.icon = Marker.SELECT_RATIO_BIG.getMark(requireContext()).toBitmap()
        drawTextOnMarker(requireContext(), tMapMarkerItem2.icon, "B")
        setLayoutRatioLocationView(
            name = location.name,
            textView = binding.tvNameB,
            markerLayout = binding.layoutMarkerB,
            closeLayout = binding.layoutCloseB
        )
        ratioLocationB = location
    }

    private fun setSelectedMarkerToRatioLocationA(tMapMarkerItem2 : TMapMarkerItem2, location: LocationData) {
        tMapMarkerItem2.icon = Marker.SELECT_RATIO_BIG.getMark(requireContext()).toBitmap()
        drawTextOnMarker(requireContext(), tMapMarkerItem2.icon, "A")
        setLayoutRatioLocationView(
            name = location.name,
            textView = binding.tvNameA,
            markerLayout = binding.layoutMarkerA,
            closeLayout = binding.layoutCloseA
        )
        ratioLocationA = location
    }


    private fun setLayoutRatioLocationView(
        name: String, textView: TextView, markerLayout: ViewGroup, closeLayout: ViewGroup
    ) {
        textView.text = name
        textView.setTextColor(Color.TEXT_ACTIVE.getColor(requireContext()))
        markerLayout.background = Marker.SELECT_RATIO.getMark(requireContext())
        closeLayout.visibility = VISIBLE
    }

    private fun isSetLocationAandB() =
        binding.tvNameA.text != "위치를 입력해주세요" && binding.tvNameB.text != "위치를 입력해주세요"

    private fun isNotSetRatioLocationA() = binding.tvNameA.text == "위치를 입력해주세요"

    private fun isNotSetRatioLocationB() = binding.tvNameB.text == "위치를 입력해주세요"

}