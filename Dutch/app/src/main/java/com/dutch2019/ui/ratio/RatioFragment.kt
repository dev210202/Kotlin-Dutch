package com.dutch2019.ui.ratio

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.dutch2019.base.BaseFragment
import com.dutch2019.R
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
    private val tMapView by lazy { TMapView(context) }
    lateinit var markerItemABitmap: Bitmap
    lateinit var markerItemBBitmap: Bitmap
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        markLocationList(tMapView, requireContext(), vm.getLocationList())
        mapAutoZoom(tMapView, vm.getLocationList(), vm.getSearchPoint())
        setMarkerClickEvent(tMapView)

        binding.layoutRatio.addView(tMapView)
        binding.layoutCloseA.setOnClickListener(OnCloseClickListener())
        binding.layoutCloseB.setOnClickListener(OnCloseClickListener())

        binding.btnRatioComplete.setOnClickListener { button ->
            if (button.isSelected) {
                findNavController().navigate(RatioFragmentDirections.actionRatioFragmentToRatioSelectFragment())
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

                    vm.clearSetRatioLocationA()
                    binding.layoutCloseA.visibility = INVISIBLE
                }
                binding.layoutCloseB -> {
                    id = binding.tvNameB.text.toString()
                    textView = binding.tvNameB
                    markerLayout = binding.layoutMarkerB
                    previousIconBitmap = markerItemBBitmap

                    vm.clearSetRatioLocationB()
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

            if (vm.isNotSetRatioLocationA() && isNotEqualLocationAorB(
                    location, vm.getRatioLocationA(), vm.getRatioLocationB()
                )
            ) {
                markerItemABitmap = tMapMarkerItem2.icon

                vm.setRatioLocationA(location)
                changeSelectRatioMark(tMapMarkerItem2, requireContext())
                drawTextOnMarker(requireContext(), tMapMarkerItem2.icon, "A")
                setLayoutRatioLocationView(
                    name = location.name,
                    textView = binding.tvNameA,
                    markerLayout = binding.layoutMarkerA,
                    closeLayout = binding.layoutCloseA
                )

            } else if (vm.isNotSetRatioLocationB() && isNotEqualLocationAorB(
                    location, vm.getRatioLocationA(), vm.getRatioLocationB()
                )
            ) {
                markerItemBBitmap = tMapMarkerItem2.icon
                vm.setRatioLocationB(location)
                changeSelectRatioMark(tMapMarkerItem2, requireContext())
                drawTextOnMarker(requireContext(), tMapMarkerItem2.icon, "B")
                setLayoutRatioLocationView(
                    name = location.name,
                    textView = binding.tvNameB,
                    markerLayout = binding.layoutMarkerB,
                    closeLayout = binding.layoutCloseB
                )

            } else {
                // Toast
            }

            if (isSetLocationAandB()) {
                setButtonState(binding.btnRatioComplete, ButtonState.DEFAULT)
            }
        }
    }

    private fun isNotEqualLocationAorB(
        location: LocationData, locationA: LocationData, locationB: LocationData
    ) = locationA != location && locationB != location


    private fun setLayoutRatioLocationView(
        name: String, textView: TextView, markerLayout: ViewGroup, closeLayout: ViewGroup
    ) {
        textView.text = name
        textView.setTextColor(getChangedTextColor(requireContext()))
        markerLayout.background = getSelectRatioMarkerBackground(requireContext())
        closeLayout.visibility = VISIBLE
    }

    private fun isSetLocationAandB() = !vm.isNotSetRatioLocationA() && !vm.isNotSetRatioLocationB()


}